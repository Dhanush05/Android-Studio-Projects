package com.dhanush.nyschools_photon.view

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Card
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.dhanush.nyschools_photon.di.DaggerApiComponent
import com.dhanush.nyschools_photon.model.School
import com.dhanush.nyschools_photon.model.SchoolsService
import com.dhanush.nyschools_photon.repository.SchoolRepository
import com.dhanush.nyschools_photon.view.ui.theme.NYSchools_PhotonTheme
import com.dhanush.nyschools_photon.viewmodel.ListViewModel
import com.dhanush.nyschools_photon.viewmodel.ViewModelFactory
import javax.inject.Inject

class MainActivity2 : ComponentActivity() {

//    private val schoolsService = SchoolsService()
//    private val schoolRepository = SchoolRepository(schoolsService)
//    private val viewModelFactory = ViewModelFactory(schoolRepository)
//    private val viewModel: ListViewModel by viewModels { viewModelFactory }
//    //private val viewModel : ListViewModel by viewModels()

    @Inject
    lateinit var viewModelFactory: ViewModelFactory
    private val listViewModel: ListViewModel by viewModels{viewModelFactory}
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        DaggerApiComponent.create().inject(this)
        setContent {
            SchoolListScreen(viewModel = listViewModel)
        }
    }
    @Composable
    fun SchoolListScreen(viewModel: ListViewModel){
        val schools by remember{viewModel.schools}.observeAsState(emptyList())
        val schoolLoadError by remember{viewModel.schoolsLoadError}.observeAsState(false)
        //intial refresh
        LaunchedEffect(Unit){
            viewModel.refresh()
        }
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp)
        ){
            if(schoolLoadError){
                Text(text = "Error loading schools")
            }else{
                SchoolList(schools = schools)
            }
        }
    }

    @Composable
    private fun SchoolList(schools: List<School>?) {
        LazyColumn{
            items(schools.orEmpty()){ school->
                SchoolListItem(school = school)
            }
        }
    }

    @Composable
    private fun SchoolListItem(school: School) {
        Card(
            modifier = Modifier
                .fillMaxWidth()
                .padding(8.dp)
        ){
            Column(
                modifier = Modifier
                    .padding(16.dp)
            ){
                Text(text = school.schoolName!!, fontWeight = FontWeight.Bold)
            }
        }

    }
}

