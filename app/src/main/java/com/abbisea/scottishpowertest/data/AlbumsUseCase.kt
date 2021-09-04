package com.abbisea.scottishpowertest.data

import com.abbisea.scottishpowertest.data.api.PlaceholderService
import com.abbisea.scottishpowertest.data.models.Album
import javax.inject.Inject

class AlbumsUseCase @Inject constructor(
    private val placeholderService: PlaceholderService
) {
    suspend fun getAlbums() = placeholderService.getAlbums().map { dto ->
        Album(id = dto.id, title = dto.title)
    }
}