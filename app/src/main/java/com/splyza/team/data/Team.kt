package com.splyza.team.data

data class Team(
    val id: String,
    val members: Members,
    val plan: Plan
)

data class Members(
    val administrators: Int,
    val editors: Int,
    val managers: Int,
    val members: Int,
    val supporters: Int,
    val total: Int
)

data class Plan(
    val memberLimit: Int,
    val supporterLimit: Int
)

enum class Roles(val title: String) {
    Manager("Coach"),
    Editor("Player Coach"),
    Member("Player"),
    Readonly("Supporter"),
}