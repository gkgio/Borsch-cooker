package com.gkgio.borsch_cooker.utils.events

import com.gkgio.borsch_cooker.meals.addlunch.LunchEvent
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class LunchMealsChanged @Inject constructor() : LunchEvent()