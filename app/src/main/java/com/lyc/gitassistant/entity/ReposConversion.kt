package com.lyc.gitassistant.entity

import android.content.Context
import com.lyc.gitassistant.entity.response.GitRepositoryEntity
import com.lyc.gitassistant.entity.uiEntity.ReposUIEntity


/**
 * 仓库相关实体转换
 */
object ReposConversion {

    fun reposToReposUIModel(context: Context, repository: GitRepositoryEntity?): ReposUIEntity {
        val reposUIEntity = ReposUIEntity()
        reposUIEntity.hideWatchIcon = true
        reposUIEntity.ownerName = repository?.owner?.login ?: ""
        reposUIEntity.ownerPic = repository?.owner?.avatarUrl ?: ""
        reposUIEntity.repositoryDes = repository?.description ?: ""
        reposUIEntity.repositoryName = repository?.name ?: ""
        reposUIEntity.repositoryFork = repository?.forksCount?.toString() ?: ""
        reposUIEntity.repositoryStar = repository?.stargazersCount?.toString() ?: ""
        reposUIEntity.repositoryWatch = repository?.subscribersCount?.toString() ?: ""
        reposUIEntity.repositoryType = repository?.language ?: ""
        reposUIEntity.repositorySize = (((repository?.size
                ?: 0) / 1024.0)).toString().substring(0, 3) + "M"
        reposUIEntity.repositoryIssue = repository?.openIssuesCount?.toString() ?: ""
        return reposUIEntity
    }

}