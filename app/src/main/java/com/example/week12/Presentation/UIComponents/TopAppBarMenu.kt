package com.example.week12.Presentation.UIComponents

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.example.week12.R

@Composable
fun TopAppBarMenu(
    changeTheme: () -> Unit,
    changeLanguage: () -> Unit
){
    var expanded by remember{ mutableStateOf( false )}
    val changeThemeText = stringResource(R.string.ChangeTheme)
    val changeLanguageText = stringResource(R.string.ChangeLanguage)
    val items = listOf(changeThemeText,changeLanguageText)
    Box(
        modifier = Modifier
            .wrapContentSize(Alignment.Center)
            .padding(15.dp)
    ){
        IconButton(onClick = {expanded = true }) {
            Icon(Icons.Filled.Menu, contentDescription = "Menu")
        }
        DropdownMenu(
            expanded = expanded,
            onDismissRequest = {expanded = false}
        ){
            items.forEach{item->
                DropdownMenuItem(
                    text = { Text(item)},
                    onClick = {
                        when(item){
                            changeThemeText -> {changeTheme()}
                            changeLanguageText -> {changeLanguage()}
                        }
                    }
                )
            }
        }
    }
}