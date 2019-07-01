package com.raul.androidapps.softwareteststarling.network.responses

import com.raul.androidapps.softwareteststarling.model.Feed

data class FeedsResponse constructor(
    val feedItems: List<Feed>
)

