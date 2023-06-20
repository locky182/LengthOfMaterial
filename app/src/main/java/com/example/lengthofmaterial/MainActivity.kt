@file:OptIn(ExperimentalMaterial3Api::class)

package com.example.lengthofmaterial

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Switch
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import com.example.lengthofmaterial.ui.theme.LengthOfMaterialTheme
import java.text.NumberFormat
import kotlin.math.pow


const val PI = 3.14

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            LengthOfMaterialTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    LengthOfMaterialApp()

                }
            }
        }
    }
}

@Composable
fun LengthOfMaterialApp(modifier: Modifier = Modifier){

    var diameterOfRoll by remember {mutableStateOf("")}
    var diameterOfTube by remember {mutableStateOf("")}
    var thicknessOfMaterial by remember {mutableStateOf("")}
    var roundUp by remember { mutableStateOf(false) }

    val dOR = diameterOfRoll.toDoubleOrNull() ?: 0.0
    val dOT = diameterOfTube.toDoubleOrNull() ?: 0.0
    val tOM = thicknessOfMaterial.toDoubleOrNull() ?: 0.0
    val result = calculateLength(dOR, dOT, tOM, roundUp)





    Column(
        modifier = modifier
            .padding(50.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center

    ) {

        Text(
            text = stringResource(R.string.calculate_length),
                       modifier = Modifier
                           .padding(bottom = 32.dp)
                           .align(alignment = Alignment.Start)
        )

        EditNumberField(
            label = R.string.diameter_of_roll,
            leadingIcon = R.drawable.number,
            keyboardOptions = KeyboardOptions.Default.copy(
                keyboardType = KeyboardType.Number,
                imeAction = ImeAction.Next
            ),
            value = diameterOfRoll,
            onValueChanged = {diameterOfRoll = it},
            modifier = Modifier
                .padding(bottom = 32.dp)
                .fillMaxWidth()
                .clip(CircleShape)
        )

        EditNumberField(
            label = R.string.diameter_of_tube,
            leadingIcon = R.drawable.number,
            keyboardOptions = KeyboardOptions.Default.copy(
                keyboardType = KeyboardType.Number,
                imeAction = ImeAction.Next
            ),
            value = diameterOfTube,
            onValueChanged = {diameterOfTube = it},
            modifier = Modifier
                .padding(bottom = 32.dp)
                .fillMaxWidth()
                .clip(CircleShape)
        )

        EditNumberField(
            label = R.string.thickness_of_material,
            leadingIcon = R.drawable.number,
            keyboardOptions = KeyboardOptions.Default.copy(
                keyboardType = KeyboardType.Number,
                imeAction = ImeAction.Done
            ),
            value = thicknessOfMaterial,
            onValueChanged = {thicknessOfMaterial = it},
            modifier = Modifier
                .padding(bottom = 32.dp)
                .fillMaxWidth()
                .clip(CircleShape)

        )

        RoundTheTipRow(roundUp = roundUp, onRoundUpChanged = {roundUp = it} )
        
        Text(text = stringResource(R.string.length_amount, result),
        style = MaterialTheme.typography.displaySmall
        )
        
        Spacer(modifier = Modifier.padding(bottom = 150.dp))



    }

}


@Composable
fun RoundTheTipRow(
    roundUp: Boolean,
    onRoundUpChanged: (Boolean) -> Unit,
    modifier: Modifier = Modifier
) {
    Row(
        modifier = modifier
            .fillMaxWidth()
            .size(48.dp),
    ) {
        Text(text = stringResource(R.string.round))
        Switch(
            modifier = Modifier
                .fillMaxWidth()
                .wrapContentWidth(Alignment.End),
            checked = roundUp,
            onCheckedChange = onRoundUpChanged
        )
    }
}



@Composable
fun EditNumberField(
    @StringRes label: Int,
    @DrawableRes leadingIcon: Int,
    keyboardOptions: KeyboardOptions,
    value: String,
    onValueChanged: (String) -> Unit,
    modifier: Modifier = Modifier
) {
    TextField(
        value = value,
        singleLine = true,
        leadingIcon = { Icon(painter = painterResource(id = leadingIcon), null) },
        modifier = modifier,
        onValueChange = onValueChanged,
        label = { Text(stringResource(label)) },
        keyboardOptions = keyboardOptions
    )
}

 fun calculateLength(dOR: Double, dOT: Double, thickness: Double,  roundUp: Boolean): String {

    val dOR2 = dOR.pow(2.0)
    val dOT2 = dOT.pow(2.0)
    var result = PI * (dOR2 - dOT2) / (4 * thickness)
    if (roundUp) {
        result = kotlin.math.ceil(result)
    }
    return NumberFormat.getCurrencyInstance().format(result)
}