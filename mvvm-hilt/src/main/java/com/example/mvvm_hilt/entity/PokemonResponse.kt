package com.example.mvvm_hilt.entity

/**
 * create by silladus 2020/7/23
 * github:https://github.com/silladus
 * des:
 */
data class PokemonResponse(
        /*@field:Json(name = "count")*/ val count: Int,
        /*@field:Json(name = "next") */val next: String?,
        /*@field:Json(name = "previous")*/ val previous: String?,
        /*@field:Json(name = "results")*/ val results: List<Pokemon>
)