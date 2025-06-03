package com.example.lifehub.features.progress

import androidx.annotation.RawRes
import androidx.annotation.StringRes
import com.example.core.analytics.Page
import com.example.wpinterviewpractice.R

enum class ProgressItems(
    @RawRes val lottie: Int,
    @StringRes val title: Int,
    @StringRes val subTitle: Int,
    val route: String = ""
) {
    GOAL(
        lottie = R.raw.anim_goal,
        title = R.string.goal_title,
        subTitle = R.string.goal_subTitle,
    ),
    TODO(
        lottie = R.raw.anim_todo_list,
        title = R.string.todo_title,
        subTitle = R.string.todo_subTitle,
        route = Page.TODO_LIST.route
    ),
    MOOD(
        lottie = R.raw.anim_mood_habit,
        title = R.string.mood_title,
        subTitle = R.string.mood_subTitle
    ),
    REFLECTION(
        lottie = R.raw.anim_calendar_reflection,
        title = R.string.reflection_title,
        subTitle = R.string.reflection_subTitle
    )
}