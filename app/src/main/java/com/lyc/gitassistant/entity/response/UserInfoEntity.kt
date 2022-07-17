package com.lyc.gitassistant.entity.response


import com.google.gson.annotations.SerializedName
import java.util.*

/**
 * {
"login": "octocat",
"id": 1,
"node_id": "MDQ6VXNlcjE=",
"avatar_url": "https://github.com/images/error/octocat_happy.gif",
"gravatar_id": "",
"url": "https://api.github.com/users/octocat",
"html_url": "https://github.com/octocat",
"followers_url": "https://api.github.com/users/octocat/followers",
"following_url": "https://api.github.com/users/octocat/following{/other_user}",
"gists_url": "https://api.github.com/users/octocat/gists{/gist_id}",
"starred_url": "https://api.github.com/users/octocat/starred{/owner}{/repo}",
"subscriptions_url": "https://api.github.com/users/octocat/subscriptions",
"organizations_url": "https://api.github.com/users/octocat/orgs",
"repos_url": "https://api.github.com/users/octocat/repos",
"events_url": "https://api.github.com/users/octocat/events{/privacy}",
"received_events_url": "https://api.github.com/users/octocat/received_events",
"type": "User",
"site_admin": false,
"name": "monalisa octocat",
"company": "GitHub",
"blog": "https://github.com/blog",
"location": "San Francisco",
"email": "octocat@github.com",
"hireable": false,
"bio": "There once was...",
"twitter_username": "monatheoctocat",
"public_repos": 2,
"public_gists": 1,
"followers": 20,
"following": 0,
"created_at": "2008-01-14T04:33:35Z",
"updated_at": "2008-01-14T04:33:35Z",
"private_gists": 81,
"total_private_repos": 100,
"owned_private_repos": 100,
"disk_usage": 10000,
"collaborators": 8,
"two_factor_authentication": true,
"plan": {
"name": "Medium",
"space": 400,
"private_repos": 20,
"collaborators": 0
}
}
 */
class UserInfoEntity {

    var login: String? = null
    var id: String? = null
    var name: String? = null
    @SerializedName("avatar_url")
    var avatarUrl: String? = null
    @SerializedName("html_url")
    var htmlUrl: String? = null
    var type: String? = null
    var company: String? = null
    var blog: String? = null
    var location: String? = null
    var email: String? = null
    var bio: String? = null

    var starRepos: Int? = null
    var honorRepos: Int? = null
    @SerializedName("public_repos")
    var publicRepos: Int = 0
    @SerializedName("public_gists")
    var publicGists: Int = 0
    var followers: Int = 0
    var following: Int = 0
    @SerializedName("created_at")
    var createdAt: Date? = null
    @SerializedName("updated_at")
    var updatedAt: Date? = null


}
