package com.splyza.team.api

import com.splyza.team.data.*
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.Path
import retrofit2.mock.BehaviorDelegate
import retrofit2.mock.Calls
import retrofit2.mock.MockRetrofit
import retrofit2.mock.NetworkBehavior
import kotlin.random.Random

class MockService : ApiService {

    private val members = Members(
        total = 89,
        administrators = 1,
        managers = 18,
        editors = 6,
        members = 58,
        supporters = 6
    )
    private val plan = arrayOf(
        Plan(
            memberLimit = 100,
            supporterLimit = 20
        ), Plan(
            memberLimit = 100,
            supporterLimit = 0
        ), Plan(
            memberLimit = 80,
            supporterLimit = 0
        )
    )

    override fun getTeam(teamId: String): Call<Team> {
        val team = Team(id = teamId, members = members, plan = plan[Random.nextInt(3)])
        val response = Calls.response(team)
        return API_DELEGATE.returning(response).getTeam(teamId)
    }

    override fun invite(
        @Path("teamId") teamId: String,
        @Body role: RoleBody
    ): Call<UrlResponse> {
        val response =
            Calls.response(UrlResponse(url = ApiService.INVITE_PATH + "/" + encodeAsUuid))
        return API_DELEGATE.returning(response).invite(teamId = teamId, role = role)
    }

    // ToDo: use algorithm to encode/decode with 13-bytes Uuid.
    private val encodeAsUuid = "eyJpbnZpdGVJZ"

    companion object {
        private val behavior: NetworkBehavior = NetworkBehavior.create()
        private val API_DELEGATE: BehaviorDelegate<ApiService> by lazy {
            MockRetrofit.Builder(ApiService.RETROFIT).networkBehavior(behavior).build()
                .create(ApiService::class.java)
        }
        val INSTANCE: MockService by lazy { MockService() }
    }
}