package com.splyza.team.data

import com.google.gson.annotations.SerializedName

data class Team(
    @SerializedName("id")
    val id: String,
    @SerializedName("members")
    val members: Members,
    @SerializedName("plan")
    val plan: Plan
)

data class Members(
    @SerializedName("administrators")
    val administrators: Int,
    @SerializedName("editors")
    val editors: Int,
    @SerializedName("managers")
    val managers: Int,
    @SerializedName("members")
    val members: Int,
    @SerializedName("supporters")
    val supporters: Int,
    @SerializedName("total")
    val total: Int
)

data class Plan(
    @SerializedName("memberLimit")
    val memberLimit: Int,
    @SerializedName("supporterLimit")
    val supporterLimit: Int
)

enum class Roles(val title: String) {
    Manager("Coach"),
    Editor("Player Coach"),
    Member("Player"),
    Readonly("Supporter"),
}

data class RoleBody(
    @SerializedName("role")
    val role: String
)

data class UrlResponse(
    @SerializedName("url")
    val url: String
)