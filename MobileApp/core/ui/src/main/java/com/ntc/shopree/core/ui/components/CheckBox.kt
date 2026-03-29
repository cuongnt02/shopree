package com.ntc.shopree.core.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.selection.toggleable
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.semantics.Role
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.ntc.shopree.core.ui.icons.Icons
import com.ntc.shopree.core.ui.theme.ColorGrey100
import com.ntc.shopree.core.ui.theme.ColorGrey300
import com.ntc.shopree.core.ui.theme.ColorGrey600

//@Composable
//fun CheckBox(
//    selected: Boolean,
//    onChecked: () -> Unit,
//    modifier: Modifier = Modifier,
//    enabled: Boolean = true
//) {
//    IconButton(onClick = onChecked, enabled = enabled, modifier = Modifier.size(24.dp)) {
//        Icon(
//            imageVector = if (selected) Icons.Filled.CheckCircle else Icons.Outlined.CheckCircle,
//            contentDescription = "check box icon",
//            tint = ColorSecondary300,
//            modifier = Modifier.background(Color.Transparent, shape = CircleShape)
//        )
//    }
//}

@Composable
fun CheckBox(
    selected: Boolean,
    onChecked: (Boolean) -> Unit,
    modifier: Modifier = Modifier,
    enabled: Boolean = true
) {
    Box(
        modifier = Modifier
            .background(
                if (selected) ColorGrey600 else Color.Transparent, shape = RoundedCornerShape(4.dp)
            )
            .size(32.dp)
            .border(
                width = 1.dp,
                color = if (selected) ColorGrey600 else ColorGrey300,
                shape = RoundedCornerShape(4.dp)
            )
            .toggleable(
                value = selected,
                role = Role.Checkbox,
                enabled = enabled,
                onValueChange = { onChecked(!selected) })
    ) {
        if (selected) Icon(
            imageVector = Icons.Outlined.Check,
            contentDescription = "check box icon",
            tint = ColorGrey100,
            modifier = Modifier.fillMaxSize()
        )
    }

}

@Composable
@Preview(backgroundColor = 0xFFFFFFFF, showBackground = true)
fun CheckboxPreview(modifier: Modifier = Modifier) {
    var select by remember { mutableStateOf(false) }
    Box(modifier = Modifier.padding(4.dp)) {
        CheckBox(selected = select, onChecked = {
            select = it
        })
    }
}

@Composable
@Preview(backgroundColor = 0xFFFFFFFF, showBackground = true)
fun CheckboxSelectedPreview(modifier: Modifier = Modifier) {

    CheckBox(selected = true, onChecked = { it ->

    })
}
