// SPDX-License-Identifier: GPL-3.0-only
package org.idumishmi.keyboard.settings.dialogs

import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import org.idumishmi.keyboard.settings.Theme
import org.idumishmi.keyboard.settings.previewDark

@Composable
fun InfoDialog(
    message: String,
    onDismissRequest: () -> Unit
) {
    ThreeButtonAlertDialog(
        onDismissRequest = onDismissRequest,
        content = { Text(message) },
        cancelButtonText = stringResource(android.R.string.ok),
        onConfirmed = { },
        confirmButtonText = null
    )
}

@Preview
@Composable
private fun Preview() {
    Theme(previewDark) {
        InfoDialog("message") { }
    }
}
