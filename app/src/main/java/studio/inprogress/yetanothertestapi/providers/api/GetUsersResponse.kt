package studio.inprogress.yetanothertestapi.providers.api

import studio.inprogress.yetanothertestapi.domain.model.UserEntity

data class GetUsersResponse(
    val total_count: Int,
    val items: List<UserEntity>
)