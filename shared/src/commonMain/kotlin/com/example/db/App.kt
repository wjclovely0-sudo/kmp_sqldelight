package com.example.db

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun App(viewModel: AppViewModel) {
    MaterialTheme {
        val notes by viewModel.notes.collectAsState()
        var title by remember { mutableStateOf("") }
        var content by remember { mutableStateOf("") }
        var editingNote by remember { mutableStateOf<Note?>(null) }

        Scaffold(
            modifier = Modifier.fillMaxSize(),
            topBar = {
                TopAppBar(title = { Text("笔记增删改查示例") })
            }
        ) { padding ->
            Column(
                modifier = Modifier
                    .padding(padding)
                    .padding(16.dp)
                    .fillMaxSize()
            ) {
                // 输入区域
                OutlinedTextField(
                    value = title,
                    onValueChange = { title = it },
                    label = { Text("标题") },
                    modifier = Modifier.fillMaxWidth()
                )
                Spacer(modifier = Modifier.height(8.dp))
                OutlinedTextField(
                    value = content,
                    onValueChange = { content = it },
                    label = { Text("内容") },
                    modifier = Modifier.fillMaxWidth()
                )
                Spacer(modifier = Modifier.height(8.dp))
                Button(
                    onClick = {
                        if (title.isNotBlank() && content.isNotBlank()) {
                            val currentEdit = editingNote
                            if (currentEdit != null) {
                                viewModel.updateNote(currentEdit.id, title, content)
                                editingNote = null
                            } else {
                                viewModel.addNote(title, content)
                            }
                            title = ""
                            content = ""
                        }
                    },
                    modifier = Modifier.align(Alignment.End)
                ) {
                    Text(if (editingNote != null) "更新笔记" else "添加笔记")
                }

                Spacer(modifier = Modifier.height(16.dp))
                HorizontalDivider()
                Spacer(modifier = Modifier.height(16.dp))

                // 列表区域
                LazyColumn(modifier = Modifier.fillMaxSize()) {
                    items(notes) { note ->
                        Card(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(vertical = 4.dp)
                        ) {
                            Row(
                                modifier = Modifier
                                    .padding(16.dp)
                                    .fillMaxWidth(),
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                Column(modifier = Modifier.weight(1f)) {
                                    Text(text = note.title, style = MaterialTheme.typography.titleMedium)
                                    Text(text = note.content, style = MaterialTheme.typography.bodyMedium)
                                }
                                TextButton(onClick = {
                                    editingNote = note
                                    title = note.title
                                    content = note.content
                                }) {
                                    Text("编辑")
                                }
                                TextButton(onClick = { viewModel.deleteNote(note.id) }) {
                                    Text("删除")
                                }
                            }
                        }
                    }
                }
            }
        }
    }
}
