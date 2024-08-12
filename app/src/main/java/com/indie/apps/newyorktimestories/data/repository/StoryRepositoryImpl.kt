package com.indie.apps.newyorktimestories.data.repository

import com.indie.apps.newyorktimestories.data.api.ApiService
import com.indie.apps.newyorktimestories.data.db.ArticleDao
import com.indie.apps.newyorktimestories.data.model.Article
import com.indie.apps.newyorktimestories.util.Resource
import kotlinx.coroutines.async
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.flow

class StoryRepositoryImpl(private val apiService: ApiService, private val articleDao: ArticleDao) :
    StoryRepository {
    override suspend fun getTopStories(section: String) = apiService.getTopStories(section)

    override suspend fun searchRecordsFromSection(
        search: String,
        section: String
    ): Flow<Resource<List<Article>>> = flow {
        coroutineScope {
            val dbResult = articleDao.searchRecordsFromSection(
                searchQuery = search,
                section = section
            )
            emit(Resource.Success(dbResult))
        }
    }
        .distinctUntilChanged()


    /*override suspend fun getRecordsFromSection(
        section: String
    ): Flow<Resource<List<Article>>> = flow {
        // println("aaaaa 111 $section")
        emit(Resource.Loading<List<Article>>())
        coroutineScope {
            val dbResultDeferred = async {
                articleDao.getRecordsFromSection(
                    section = section
                )
            }

            //println("aaaaaa 222")
            val apiResultDeferred = async { apiService.getTopStories(section) }

            dbResultDeferred.await()
            // println("aaaaaaa 3333")
            apiResultDeferred.await()

            //println("aaaaaaa 44444")
            val apiRes = apiResultDeferred.getCompleted()
            val dbRes = dbResultDeferred.getCompleted()

            //println("aaaaaaa api = ${apiRes}")
            //println("aaaaaaa database = ${dbRes}")

            //if(dbRes.isNullOrEmpty()){
            if (apiRes.isSuccessful) {
                //println("aaaaaaa 55555")
                if (apiRes.body() != null) {
                    //println("aaaaaaa 666666 ${apiRes.body()?.results}")
                    val deleterow = articleDao.deleteRecordsFromSection(section)
                    // val deleterow = articleDao.deleteRecordsFromSection()
                    //println("aaaaaa delete row = ${deleterow}")
                    val insert = articleDao.insertAll(apiRes.body()!!.results.map { item -> item.copy(section = section) })

                    //println("aaaaaa insert row = ${insert.size}")

                    emit(Resource.Success(apiRes.body()!!.results))
                } else {
                    //println("aaaaaaa 777777")
                    emit(Resource.Success(dbRes))
                }

            } else if(dbRes.size >0) {
                //println("aaaaaaa 888888 $dbRes")
                emit(Resource.Success(dbRes))
            }else if(dbRes.size >0 ){
                //println("aaaaaaa 8888999 $dbRes")
                emit(Resource.Success(dbRes))
            }else{
                emit(Resource.Error<List<Article>>(apiRes.message()))
            }


        }
    }
        .distinctUntilChanged()*/

    override suspend fun getRecordsFromSection(
        section: String
    ): Flow<Resource<List<Article>>> = flow {
        emit(Resource.Loading<List<Article>>())
        coroutineScope {


            val apiResultDeferred = async { apiService.getTopStories(section) }


            apiResultDeferred.await()

            val apiRes = apiResultDeferred.getCompleted()



            if (apiRes.isSuccessful) {
                if (apiRes.body() != null) {
                    val deleterow = articleDao.deleteRecordsFromSection(section)
                    val insert =
                        articleDao.insertAll(apiRes.body()!!.results.map { item -> item.copy(section = section) })

                    //emit(Resource.Success(apiRes.body()!!.results))
                }

            }

            val dbResultDeferred = async {
                articleDao.getRecordsFromSection(
                    section = section
                )
            }

            dbResultDeferred.await()

            val dbRes = dbResultDeferred.getCompleted()

            if (dbRes.isNotEmpty()) {
                emit(Resource.Success(dbRes))
            } else {
                emit(Resource.Error<List<Article>>(apiRes.message()))
            }

        }
    }
        .distinctUntilChanged()

    override suspend fun getRecordsFromId(id: Long) = articleDao.getRecordsFromId(id)
}