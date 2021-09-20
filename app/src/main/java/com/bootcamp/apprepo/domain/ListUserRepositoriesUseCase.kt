package com.bootcamp.apprepo.domain

import com.bootcamp.apprepo.core.UseCase
import com.bootcamp.apprepo.data.model.Repo
import com.bootcamp.apprepo.data.repositories.RepoRepository
import kotlinx.coroutines.flow.Flow

class ListUserRepositoriesUseCase(
    private val repository: RepoRepository
    ) : UseCase<String, List<Repo>>() {

    override suspend fun execute(param: String): Flow<List<Repo>> {
        return repository.listRepositories(param)
    }
}