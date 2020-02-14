package com.yasin.okcredit

/**
 * Created by Yasin on 4/2/20.
 */

const val BASE_URL = "https://api.nytimes.com/svc/topstories/v2/"
const val API_KEY = "xI4AA4gcMj9JyFlyQn2dSAj689PGjKjA"
const val DATABASE_NAME = "news_db"

//Model
const val THUMBNAIL = "thumbLarge"
const val COVER_PHOTO = "mediumThreeByTwo210"
const val NEWS_ID = "id"
const val NEWS_TYPE = "type"
const val HOME_NEWS = "home"
const val SCIENCE_NEWS = "science"
const val MOVIES_NEWS = "movies"
const val SPORTS_NEWS = "sports"

//Local Repository
const val FETCH_TIME_OUT  = 1*60*1000
const val GET_ALL_HOME_NEWS_QUERY = "SELECT * FROM HomeNews"
const val GET_HOME_NEWS_DETAILS_QUERY = "SELECT * FROM HomeNews WHERE :id = id"