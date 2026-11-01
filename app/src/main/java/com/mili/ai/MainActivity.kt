package com.mili.ai

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

private val Night = Color(0xFF090B18)
private val Panel = Color(0xFF15182B)
private val Violet = Color(0xFF9B7BFF)
private val Cyan = Color(0xFF67E8F9)
private val Muted = Color(0xFF9BA3C7)
private val White = Color(0xFFF6F5FF)

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent { MiliApp() }
    }
}

@Composable
fun MiliApp() {
    var page by remember { mutableStateOf("Home") }
    MaterialTheme(colorScheme = darkColorScheme(
        background = Night, surface = Panel, primary = Violet,
        secondary = Cyan, onBackground = White, onSurface = White
    )) {
        Scaffold(
            containerColor = Night,
            bottomBar = {
                NavigationBar(containerColor = Color(0xFF101326), contentColor = White) {
                    listOf(
                        Triple("Home", Icons.Default.Home, "হোম"),
                        Triple("Chat", Icons.Default.Chat, "চ্যাট"),
                        Triple("Voice", Icons.Default.GraphicEq, "ভয়েস"),
                        Triple("Settings", Icons.Default.Settings, "সেটিংস")
                    ).forEach { item ->
                        NavigationBarItem(
                            selected = page == item.first,
                            onClick = { page = item.first },
                            icon = { Icon(item.second, contentDescription = item.third) },
                            label = { Text(item.third, fontSize = 11.sp) },
                            colors = NavigationBarItemDefaults.colors(
                                selectedIconColor = Cyan,
                                selectedTextColor = Cyan,
                                indicatorColor = Violet.copy(alpha = 0.18f),
                                unselectedIconColor = Muted,
                                unselectedTextColor = Muted
                            )
                        )
                    }
                }
            }
        ) { padding ->
            when (page) {
                "Home" -> HomeScreen(Modifier.padding(padding), onOpen = { page = it })
                "Chat" -> ChatScreen(Modifier.padding(padding))
                "Voice" -> VoiceStudioScreen(Modifier.padding(padding))
                else -> SettingsScreen(Modifier.padding(padding), onOpen = { page = it })
            }
        }
    }
}

@Composable
private fun HomeScreen(modifier: Modifier = Modifier, onOpen: (String) -> Unit) {
    LazyColumn(
        modifier.fillMaxSize().background(Night),
        contentPadding = PaddingValues(start = 20.dp, end = 20.dp, top = 28.dp, bottom = 24.dp),
        verticalArrangement = Arrangement.spacedBy(18.dp)
    ) {
        item {
            Row(verticalAlignment = Alignment.CenterVertically) {
                Box(Modifier.size(48.dp).clip(RoundedCornerShape(16.dp)).background(
                    Brush.linearGradient(listOf(Violet, Color(0xFF4432A8)))
                ), contentAlignment = Alignment.Center) {
                    Icon(Icons.Default.AutoAwesome, null, tint = White, modifier = Modifier.size(27.dp))
                }
                Spacer(Modifier.width(12.dp))
                Column(Modifier.weight(1f)) {
                    Text("MILI AI", color = White, fontSize = 22.sp, fontWeight = FontWeight.ExtraBold, letterSpacing = 2.sp)
                    Text("YOUR PERSONAL AI COMPANION", color = Muted, fontSize = 9.sp, letterSpacing = 1.2.sp)
                }
                Icon(Icons.Default.NotificationsNone, "Notifications", tint = White, modifier = Modifier.size(25.dp))
            }
        }
        item {
            Column(
                Modifier.fillMaxWidth().clip(RoundedCornerShape(28.dp))
                    .background(Brush.linearGradient(listOf(Color(0xFF30205D), Color(0xFF151A36), Color(0xFF102A43))))
                    .padding(22.dp)
            ) {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Column(Modifier.weight(1f)) {
                        Text("শুভেচ্ছা! ✨", color = Cyan, fontSize = 13.sp, fontWeight = FontWeight.SemiBold)
                        Spacer(Modifier.height(7.dp))
                        Text("আজ কী করতে
সাহায্য করব?", color = White, fontSize = 27.sp, lineHeight = 33.sp, fontWeight = FontWeight.Bold)
                    }
                    EarthOrb()
                }
                Spacer(Modifier.height(18.dp))
                Button(onClick = { onOpen("Chat") }, colors = ButtonDefaults.buttonColors(containerColor = Violet), shape = RoundedCornerShape(15.dp)) {
                    Icon(Icons.Default.AutoAwesome, null, modifier = Modifier.size(18.dp))
                    Spacer(Modifier.width(8.dp))
                    Text("Mili-এর সাথে কথা বলুন", color = White, fontWeight = FontWeight.SemiBold)
                }
            }
        }
        item { SectionTitle("দ্রুত অ্যাকশন", "আপনার দরকারি টুল") }
        item {
            Row(horizontalArrangement = Arrangement.spacedBy(12.dp)) {
                ActionTile("Voice Studio", "নিজের ভয়েস সাজান", Icons.Default.GraphicEq, Modifier.weight(1f)) { onOpen("Voice") }
                ActionTile("AI Chat", "প্রশ্ন করুন", Icons.Default.ChatBubbleOutline, Modifier.weight(1f)) { onOpen("Chat") }
            }
        }
        item {
            Row(horizontalArrangement = Arrangement.spacedBy(12.dp)) {
                ActionTile("Earth Orb", "লুক ও অ্যানিমেশন", Icons.Default.Public, Modifier.weight(1f)) { onOpen("Orb") }
                ActionTile("Phone Tools", "শর্টকাট ও কন্ট্রোল", Icons.Default.Tune, Modifier.weight(1f)) { onOpen("Phone") }
            }
        }
        item {
            Surface(color = Panel, shape = RoundedCornerShape(22.dp)) {
                Row(Modifier.fillMaxWidth().clickable { onOpen("Voice") }.padding(17.dp), verticalAlignment = Alignment.CenterVertically) {
                    Icon(Icons.Default.Mic, null, tint = Cyan, modifier = Modifier.size(27.dp))
                    Spacer(Modifier.width(13.dp))
                    Column(Modifier.weight(1f)) {
                        Text("Ultimate Voice Studio", color = White, fontWeight = FontWeight.Bold)
                        Text("প্রোফাইল, ভাষা, স্পিড ও পিচ কাস্টমাইজ করুন", color = Muted, fontSize = 12.sp)
                    }
                    Icon(Icons.Default.ChevronRight, null, tint = Muted)
                }
            }
        }
    }
}

@Composable
private fun EarthOrb() {
    Box(
        Modifier.size(112.dp).clip(CircleShape).background(
            Brush.radialGradient(listOf(Color(0xFF7E72FF), Color(0xFF3B52B8), Color(0xFF101A3D)))
        ),
        contentAlignment = Alignment.Center
    ) {
        Box(Modifier.size(78.dp).clip(CircleShape).background(
            Brush.radialGradient(listOf(Color(0xFF77F3FF), Color(0xFF286BC0), Color(0xFF101A3D)))
        ), contentAlignment = Alignment.Center) {
            Icon(Icons.Default.Public, null, tint = Color(0xFFDDFBFF), modifier = Modifier.size(58.dp))
        }
        Box(Modifier.align(Alignment.CenterStart).offset(x = (-2).dp).size(9.dp).clip(CircleShape).background(Color(0xFFFF5C91)))
        Box(Modifier.align(Alignment.CenterEnd).offset(x = 2.dp).size(9.dp).clip(CircleShape).background(Cyan))
    }
}

@Composable
private fun SectionTitle(title: String, subtitle: String) {
    Column {
        Text(title, color = White, fontSize = 19.sp, fontWeight = FontWeight.Bold)
        Text(subtitle, color = Muted, fontSize = 12.sp)
    }
}

@Composable
private fun ActionTile(title: String, subtitle: String, icon: androidx.compose.ui.graphics.vector.ImageVector, modifier: Modifier = Modifier, onClick: () -> Unit) {
    Surface(modifier.clickable { onClick() }, color = Panel, shape = RoundedCornerShape(22.dp)) {
        Column(Modifier.padding(16.dp).heightIn(min = 104.dp), verticalArrangement = Arrangement.spacedBy(10.dp)) {
            Box(Modifier.size(42.dp).clip(RoundedCornerShape(14.dp)).background(Violet.copy(alpha = 0.17f)), contentAlignment = Alignment.Center) {
                Icon(icon, null, tint = Cyan, modifier = Modifier.size(22.dp))
            }
            Text(title, color = White, fontWeight = FontWeight.Bold, fontSize = 14.sp)
            Text(subtitle, color = Muted, fontSize = 11.sp)
        }
    }
}

@Composable
private fun ChatScreen(modifier: Modifier = Modifier) {
    var input by remember { mutableStateOf("") }
    var messages by remember { mutableStateOf(listOf("মিলি AI-তে স্বাগতম! আমি আপনার UI ডেমো সহকারী।")) }
    Column(modifier.fillMaxSize().background(Night).padding(18.dp)) {
        Text("AI CHAT", color = Cyan, fontSize = 12.sp, letterSpacing = 2.sp, fontWeight = FontWeight.Bold)
        Text("কথা বলুন মিলির সাথে", color = White, fontSize = 25.sp, fontWeight = FontWeight.Bold)
        Spacer(Modifier.height(18.dp))
        LazyColumn(Modifier.weight(1f), verticalArrangement = Arrangement.spacedBy(10.dp)) {
            items(messages.size) { index ->
                Surface(color = if (index == 0) Panel else Color(0xFF282044), shape = RoundedCornerShape(18.dp)) {
                    Text(messages[index], Modifier.padding(15.dp), color = White, fontSize = 14.sp)
                }
            }
        }
        Row(verticalAlignment = Alignment.CenterVertically) {
            OutlinedTextField(value = input, onValueChange = { input = it }, modifier = Modifier.weight(1f), placeholder = { Text("আপনার প্রশ্ন লিখুন…") }, shape = RoundedCornerShape(18.dp), singleLine = true)
            Spacer(Modifier.width(8.dp))
            IconButton(onClick = { if (input.isNotBlank()) { messages = messages + "আপনি: $input" + "ডেমো: AI API সংযুক্ত হলে এখানে উত্তর আসবে।"; input = "" } }) {
                Icon(Icons.Default.Send, "Send", tint = Cyan)
            }
        }
    }
}

@Composable
private fun VoiceStudioScreen(modifier: Modifier = Modifier) {
    var selected by remember { mutableStateOf("Mili Sweet") }
    var speed by remember { mutableFloatStateOf(1f) }
    var pitch by remember { mutableFloatStateOf(1f) }
    var language by remember { mutableStateOf("বাংলা") }
    val profiles = listOf("Mili Sweet", "Mili Royal", "Mili Robot", "Mili Custom")
    LazyColumn(modifier.fillMaxSize().background(Night), contentPadding = PaddingValues(20.dp), verticalArrangement = Arrangement.spacedBy(16.dp)) {
        item {
            Text("VOICE STUDIO", color = Cyan, fontSize = 12.sp, letterSpacing = 2.sp, fontWeight = FontWeight.Bold)
            Text("আপনার কণ্ঠ, আপনার নিয়ম।", color = White, fontSize = 25.sp, fontWeight = FontWeight.Bold)
            Text("প্রোফাইল নির্বাচন করুন এবং নিজের মতো সাজান।", color = Muted, fontSize = 13.sp)
        }
        item {
            Surface(color = Panel, shape = RoundedCornerShape(24.dp)) {
                Column(Modifier.padding(18.dp)) {
                    Text("VOICE PROFILES", color = Muted, fontSize = 11.sp, letterSpacing = 1.5.sp)
                    Spacer(Modifier.height(12.dp))
                    profiles.forEach { name ->
                        Row(Modifier.fillMaxWidth().clip(RoundedCornerShape(14.dp)).clickable { selected = name }.background(if (selected == name) Violet.copy(alpha = 0.16f) else Color.Transparent).padding(12.dp), verticalAlignment = Alignment.CenterVertically) {
                            Icon(if (name == "Mili Robot") Icons.Default.SmartToy else Icons.Default.RecordVoiceOver, null, tint = if (selected == name) Cyan else Muted)
                            Spacer(Modifier.width(10.dp))
                            Text(name, Modifier.weight(1f), color = White, fontWeight = if (selected == name) FontWeight.Bold else FontWeight.Normal)
                            if (selected == name) Icon(Icons.Default.CheckCircle, null, tint = Cyan)
                        }
                    }
                    OutlinedButton(onClick = { selected = "Mili Custom" }, modifier = Modifier.fillMaxWidth()) {
                        Icon(Icons.Default.Add, null); Spacer(Modifier.width(6.dp)); Text("নতুন প্রোফাইল তৈরি")
                    }
                }
            }
        }
        item {
            Surface(color = Panel, shape = RoundedCornerShape(24.dp)) {
                Column(Modifier.padding(18.dp), verticalArrangement = Arrangement.spacedBy(10.dp)) {
                    Text("VOICE TUNING", color = White, fontWeight = FontWeight.Bold)
                    Text("ভাষা", color = Muted)
                    Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                        FilterChip(selected = language == "বাংলা", onClick = { language = "বাংলা" }, label = { Text("বাংলা") })
                        FilterChip(selected = language == "English", onClick = { language = "English" }, label = { Text("English") })
                    }
                    Text("Speech speed · ${"%.1f".format(speed)}x", color = White)
                    Slider(value = speed, onValueChange = { speed = it }, valueRange = 0.5f..2f)
                    Text("Pitch · ${"%.1f".format(pitch)}x", color = White)
                    Slider(value = pitch, onValueChange = { pitch = it }, valueRange = 0.5f..2f)
                    Button(onClick = { }, modifier = Modifier.fillMaxWidth(), shape = RoundedCornerShape(14.dp)) {
                        Icon(Icons.Default.PlayArrow, null); Spacer(Modifier.width(8.dp)); Text("ভয়েস প্রিভিউ (ডেমো)")
                    }
                    Text("নোট: এই স্ক্রিনটি UI ডেমো। বাস্তব TTS ইঞ্জিন যুক্ত করলে প্রিভিউ ও কণ্ঠ নির্বাচন কাজ করবে।", color = Muted, fontSize = 11.sp)
                }
            }
        }
    }
}

@Composable
private fun SettingsScreen(modifier: Modifier = Modifier, onOpen: (String) -> Unit) {
    LazyColumn(modifier.fillMaxSize().background(Night), contentPadding = PaddingValues(20.dp), verticalArrangement = Arrangement.spacedBy(12.dp)) {
        item {
            Text("PREFERENCES", color = Cyan, fontSize = 12.sp, letterSpacing = 2.sp, fontWeight = FontWeight.Bold)
            Text("সেটিংস", color = White, fontSize = 28.sp, fontWeight = FontWeight.Bold)
            Text("আপনার Mili AI অভিজ্ঞতা সাজিয়ে নিন", color = Muted, fontSize = 13.sp)
        }
        item { SettingsRow("Voice Studio", "প্রোফাইল ও কণ্ঠের সেটিংস", Icons.Default.GraphicEq) { onOpen("Voice") } }
        item { SettingsRow("Earth Orb", "স্টাইল, আকার ও আলো", Icons.Default.Public) { onOpen("Orb") } }
        item { SettingsRow("Phone Tools", "সমর্থিত ফোন শর্টকাট", Icons.Default.PhoneAndroid) { onOpen("Phone") } }
        item { SettingsRow("Appearance", "Dark / AMOLED / Violet theme", Icons.Default.Palette) { } }
        item { SettingsRow("Privacy & Permissions", "অনুমতি ও গোপনীয়তা", Icons.Default.Security) { } }
        item { SettingsRow("About Mili AI", "ভার্সন ও তথ্য", Icons.Default.Info) { } }
    }
}

@Composable
private fun SettingsRow(title: String, subtitle: String, icon: androidx.compose.ui.graphics.vector.ImageVector, onClick: () -> Unit) {
    Surface(color = Panel, shape = RoundedCornerShape(20.dp), modifier = Modifier.fillMaxWidth().clickable { onClick() }) {
        Row(Modifier.padding(16.dp), verticalAlignment = Alignment.CenterVertically) {
            Box(Modifier.size(44.dp).clip(RoundedCornerShape(14.dp)).background(Violet.copy(alpha = 0.16f)), contentAlignment = Alignment.Center) {
                Icon(icon, null, tint = Cyan)
            }
            Spacer(Modifier.width(12.dp))
            Column(Modifier.weight(1f)) {
                Text(title, color = White, fontWeight = FontWeight.SemiBold)
                Text(subtitle, color = Muted, fontSize = 12.sp)
            }
            Icon(Icons.Default.ChevronRight, null, tint = Muted)
        }
    }
}
