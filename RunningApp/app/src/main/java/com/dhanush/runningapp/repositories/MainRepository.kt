package com.dhanush.runningapp.repositories

import com.dhanush.runningapp.db.Run
import com.dhanush.runningapp.db.RunDAO
import javax.inject.Inject

class MainRepository @Inject constructor(
    val runDAO: RunDAO
) {
    //provide functions to access the database
    //call functions from the dao and provide to view model
    suspend fun insertRun(run: Run) = runDAO.insertRun(run)
    suspend fun deleteRun(run: Run) = runDAO.deleteRun(run)
    fun getAllRunsSortedByDate() = runDAO.getAllRunsSortedByDate()
    fun getAllRunsSortedByTimeInMillis() = runDAO.getAllRunsSortedByTimeInMillis()
    fun getAllRunsSortedByCaloriesBurned() = runDAO.getAllRunsSortedByCaloriesBurned()
    fun getAllRunsSortedByAvgSpeed() = runDAO.getAllRunsSortedByAvgSpeed()
    fun getAllRunsSortedByDistance() = runDAO.getAllRunsSortedByDistance()
    fun getTotalTimeInMillis() = runDAO.getTotalTimeInMillis()
    fun getTotalCaloriesBurned() = runDAO.getTotalCaloriesBurned()
    fun getTotalDistance() = runDAO.getTotalDistance()
    fun getTotalAvgSpeed() = runDAO.getTotalAvgSpeed()

}