package com.example.coldstar.paging

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.example.coldstar.data.remote.ApiService
import com.example.coldstar.model.Search
import retrofit2.HttpException
import java.io.IOException

class MultiSearchSource(private val apiService: ApiService, private val searchParams: String) :
    PagingSource<Int, Search>() {
    override fun getRefreshKey(state: PagingState<Int, Search>): Int? {
        return state.anchorPosition
    }

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, Search> {
        return try {
            val nextPage = params.key ?: 1
            val data = apiService.multiSearch(page = nextPage,searchParams = searchParams)
            LoadResult.Page(
                nextKey = if (data.results.isEmpty()) null else data.page + 1,
                prevKey = if (nextPage == 1) null else nextPage - 1,
                data = data.results
            )
        } catch (e: IOException) {
            return LoadResult.Error(e)
        } catch (e: HttpException) {
            return LoadResult.Error(e)
        }
    }
}