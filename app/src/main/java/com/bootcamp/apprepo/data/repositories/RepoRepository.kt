package com.bootcamp.apprepo.data.repositories

import com.bootcamp.apprepo.data.model.Repo
import kotlinx.coroutines.flow.Flow

interface RepoRepository {

    suspend fun listRepositories(user: String): Flow<List<Repo>>
}