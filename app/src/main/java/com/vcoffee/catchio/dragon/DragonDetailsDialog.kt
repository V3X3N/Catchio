package com.vcoffee.catchio.dragon

import androidx.compose.foundation.layout.Column
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable

@Composable
fun DragonDetailsDialog(dragon: Dragon, onDismiss: () -> Unit) {
    AlertDialog(
        onDismissRequest = onDismiss,
        title = {
            Text("Dragon Details")
        },
        text = {
            Column {
                Text("Name: ${dragon.name}")
                Text("Level: ${dragon.level}")
                Text("Type: ${dragon.type}")
                Text("HP: ${dragon.hp}")
                Text("Attack: ${dragon.attack}")
                Text("Defense: ${dragon.defense}")
                Text("Speed: ${dragon.speed}")
                Text("Attacks: ${dragon.attacks}")
            }
        },
        confirmButton = {
            TextButton(onClick = onDismiss) {
                Text("Close")
            }
        }
    )
}