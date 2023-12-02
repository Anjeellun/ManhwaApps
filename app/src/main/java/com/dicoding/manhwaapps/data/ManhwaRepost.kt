package com.dicoding.manhwaapps.data

import com.dicoding.manhwaapps.model.ManhwaData
import com.dicoding.manhwaapps.model.ManhwaList
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow


class ManhwaRepost {

    private val manhwaList = mutableListOf<ManhwaList>()
    private val favManhwa = mutableListOf<String>()

    init {
        if (manhwaList.isEmpty()) {
            ManhwaData.manhwa.forEach {
                manhwaList.add(ManhwaList(it, 0))
            }
        }
    }

    fun getSortedGrouped(): Flow<Map<Char, List<ManhwaList>>> {
        return flow {
            val sortedManhwa = manhwaList.sortedBy { it.list.manhwaName }
            val groupedManhwa = sortedManhwa.groupBy { it.list.manhwaName[0] }
            emit(groupedManhwa)
        }
    }

    fun getManhwaListById(manhwaId: String): ManhwaList {
        return manhwaList.first {
            it.list.id == manhwaId
        }
    }

    fun searchManhwa(query: String): Flow<List<ManhwaList>> {
        return flow {
            val filteredManhwa = manhwaList.filter {
                it.list.manhwaName.contains(query, ignoreCase = true)
            }
            emit(filteredManhwa)
        }
    }

    fun getFavManhwa(): Flow<List<ManhwaList>> {
        return flow {
            val favManhwaListed = manhwaList.filter { it.list.id in favManhwa }
            emit(favManhwaListed)
        }
    }

    fun addManwhaToFav(manhwaId: String) {
        if (!favManhwa.contains(manhwaId)) {
            favManhwa.add(manhwaId)
        }
    }

    fun removeManhwaFav(manhwaId: String) {
        favManhwa.remove(manhwaId)
    }

    fun isFavManhwa(manhwaId: String): Boolean {
        return favManhwa.contains(manhwaId)
    }

    companion object {
        @Volatile
        private var instance: ManhwaRepost? = null

        fun getInstance(): ManhwaRepost = instance ?: synchronized(this) {
            ManhwaRepost().apply {
                instance = this
            }
        }
    }
}