package com.yekitrak.ekacare.ui.screen

import android.app.DatePickerDialog
import android.content.Context
import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import com.yekitrak.ekacare.data.db.entity.UserEntity
import com.yekitrak.ekacare.data.state.UserUiState
import com.yekitrak.ekacare.viewmodel.UserViewModel
import java.text.DateFormat
import java.util.Calendar
import java.util.Date
import java.util.Locale

@Composable
fun HomeScreen(viewModel: UserViewModel) {
    val uiState by viewModel.uiState.collectAsState()
    var showDialog by remember { mutableStateOf(false) }
    Column(modifier = Modifier.fillMaxSize()) {
        when (uiState) {
            is UserUiState.Error -> Text(text = (uiState as UserUiState.Error).message)
            is UserUiState.Success -> {
                val users = (uiState as UserUiState.Success).users
                UserList(users = users) { id ->
                    viewModel.deleteUser(id)
                }
            }
            else -> Box(modifier = Modifier.weight(1f).align(Alignment.CenterHorizontally) ) {
                Text(text = "Loading Please wait", textAlign = TextAlign.Center)
            }
        }

        Spacer(modifier = Modifier.weight(1f))  // This spacer will fill the remaining space

        if (showDialog) {
            AddUserDialog(
                onDismiss = { showDialog = false },
                onAddUser = { user ->
                    viewModel.addUser(user)
                    showDialog = false
                }
            )
        }

        Button(onClick = { showDialog = true }, modifier = Modifier.align(Alignment.CenterHorizontally)) {
            Text("Add User")
        }
    }
}

@Composable
fun UserList(users: List<UserEntity>, onDeleteUser: (Long) -> Unit) {
    LazyColumn {
        items(users) { user ->
            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(8.dp),
                shape = RoundedCornerShape(8.dp)
            ) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(8.dp),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Icon(Icons.Default.AccountCircle, contentDescription = "User", modifier = Modifier.size(40.dp))
                    Column(
                        modifier = Modifier.weight(1f).padding(start = 8.dp)
                    ) {
                        Text(text = "Name: ${user.name}")
                        Text(text = "Age: ${user.age}")
                        Text(text = "Address: ${user.address}")
                    }
                    IconButton(onClick = { onDeleteUser(user.id) }) {
                        Icon(Icons.Default.Delete, contentDescription = "Delete User")
                    }
                }
            }
        }
    }
}

@Composable
fun AddUserDialog(onDismiss: () -> Unit, onAddUser: (UserEntity) -> Unit) {
    var name by remember { mutableStateOf("") }
    var dob by remember { mutableStateOf<Date?>(null) }
    var age by remember { mutableStateOf("") }
    var address by remember { mutableStateOf("") }
    var city by remember { mutableStateOf("") }
    var country by remember { mutableStateOf("") }
    val context = LocalContext.current

    LaunchedEffect(dob) {
        val currentDate = Calendar.getInstance()
        val selectedDate = Calendar.getInstance()
        selectedDate.time = dob ?: currentDate.time
        val ageCalculated = currentDate.get(Calendar.YEAR) - selectedDate.get(Calendar.YEAR)
        age = ageCalculated.toString()
    }

    Dialog(onDismissRequest = { onDismiss() }) {
        Column(
            modifier = Modifier.padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            TextField(value = name, onValueChange = { name = it.trim() }, label = { Text("Name") })
            dob?.let { selectedDate ->
                Text(
                    text = DateFormat.getDateInstance(DateFormat.MEDIUM).format(selectedDate),
                    modifier = Modifier
                        .clickable {
                            showDatePicker(context, selectedDate) { year, month, day ->
                                val calendar = Calendar.getInstance()
                                calendar.set(year, month, day)
                                dob = calendar.time
                            }
                        }
                        .background(color = Color.White, shape = RoundedCornerShape(4.dp))
                        .border(width = 1.dp, color = Color.Black, shape = RoundedCornerShape(4.dp))
                        .fillMaxWidth(1f)
                        .padding(9.dp)
                )
            } ?: Text(
                text = "Select DOB",
                modifier = Modifier
                    .clickable {
                        val currentDate = Calendar.getInstance()
                        showDatePicker(context, currentDate.time) { year, month, day ->
                            val calendar = Calendar.getInstance()
                            calendar.set(year, month, day)
                            dob = calendar.time
                        }
                    }
                    .background(color = Color(0xffe3e3e3), shape = RoundedCornerShape(4.dp))
                    .border(width = 1.dp, color = Color.Black, shape = RoundedCornerShape(4.dp))
                    .fillMaxWidth(1f)
                    .padding(9.dp)
            )
            TextField(value = age, onValueChange = { age = it.trim() }, label = { Text("Age") })
            TextField(value = address, onValueChange = { address = it.trim() }, label = { Text("Address") })
            TextField(value = city, onValueChange = { city = it.trim() }, label = { Text("City") })
            TextField(value = country, onValueChange = { country = it.trim() }, label = { Text("Country") })

            Button(onClick = {
                if (name.isNotBlank() && dob != null && age.isNotBlank() && address.isNotBlank() && city.isNotBlank() && country.isNotBlank()) {
                    onAddUser(
                        UserEntity(
                            name = name,
                            dob = DateFormat.getDateInstance(DateFormat.MEDIUM).format(dob!!),
                            age = age.toIntOrNull() ?: 0,
                            address = address,
                            city = city,
                            country = country
                        )
                    )
                    onDismiss()
                } else {
                    Toast.makeText(context, "All fields are required", Toast.LENGTH_SHORT)
                }
            }) {
                Text("Add User")
            }
        }
    }
}

private fun showDatePicker(context: Context, selectedDate: Date, onDateSet: (Int, Int, Int) -> Unit) {
    val calendar = Calendar.getInstance()
    calendar.time = selectedDate
    val year = calendar.get(Calendar.YEAR)
    val month = calendar.get(Calendar.MONTH)
    val day = calendar.get(Calendar.DAY_OF_MONTH)

    DatePickerDialog(
        context,
        { _, selectedYear, selectedMonth, selectedDay ->
            onDateSet(selectedYear, selectedMonth, selectedDay)
        },
        year,
        month,
        day
    ).show()
}

