package com.example.hito_3.user_interface.resumeAnalyze

import android.net.Uri
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.material3.TabRowDefaults.tabIndicatorOffset
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.hito_3.data.AnalyzeModel.ResumeTextAnalysisModel
import com.example.hito_3.data.AnalyzeModel.SkillsModel
import com.example.hito_3.ui.theme.*
import org.koin.androidx.compose.koinViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ResumeAnalyzeScreen(
    viewModel: AnalyzeViewModel = koinViewModel(),
    onBackClick: () -> Unit = {}
) {
    val state by viewModel.state.collectAsState()
    val loading by viewModel.loading.collectAsState()
    val context = LocalContext.current

    var selectedTab by remember { mutableStateOf(0) }
    var resumeText by remember { mutableStateOf("") }
    var selectedFileName by remember { mutableStateOf<String?>(null) }

    val pdfPickerLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.GetContent()
    ) { uri: Uri? ->
        uri?.let {
            selectedFileName = uri.lastPathSegment
            context.contentResolver.openInputStream(uri)?.use { inputStream ->
                val bytes = inputStream.readBytes()
                viewModel.analyzePdf(bytes)
            }
        }
    }

    Scaffold(
        containerColor = NightBlack,
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        text = "Resume Analysis",
                        fontSize = 22.sp,
                        fontFamily = poppins_regular,
                        fontWeight = FontWeight.Bold,
                        color = TextWhite
                    )
                },
                navigationIcon = {
                    IconButton(onClick = onBackClick) {
                        Icon(
                            imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                            contentDescription = "Back",
                            tint = TextWhite
                        )
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = NightBlack
                )
            )
        }
    ) { padding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
                .verticalScroll(rememberScrollState())
                .padding(horizontal = 24.dp)
        ) {
            Spacer(modifier = Modifier.height(16.dp))

            // Tab Selection
            TabRow(
                selectedTabIndex = selectedTab,
                containerColor = NightSurface,
                contentColor = RedSecondary,
                indicator = { tabPositions ->
                    TabRowDefaults.SecondaryIndicator(
                        modifier = Modifier.tabIndicatorOffset(tabPositions[selectedTab]),
                        color = RedSecondary
                    )
                }
            ) {
                Tab(
                    selected = selectedTab == 0,
                    onClick = { selectedTab = 0 },
                    text = {
                        Text(
                            text = "Upload PDF",
                            fontFamily = poppins_regular,
                            fontWeight = if (selectedTab == 0) FontWeight.SemiBold else FontWeight.Normal
                        )
                    },
                    selectedContentColor = RedSecondary,
                    unselectedContentColor = TextMediumGrey
                )
                Tab(
                    selected = selectedTab == 1,
                    onClick = { selectedTab = 1 },
                    text = {
                        Text(
                            text = "Paste Text",
                            fontFamily = poppins_regular,
                            fontWeight = if (selectedTab == 1) FontWeight.SemiBold else FontWeight.Normal
                        )
                    },
                    selectedContentColor = RedSecondary,
                    unselectedContentColor = TextMediumGrey
                )
            }

            Spacer(modifier = Modifier.height(24.dp))

            // Content based on selected tab
            when (selectedTab) {
                0 -> {
                    // PDF Upload Section
                    PdfUploadSection(
                        selectedFileName = selectedFileName,
                        onUploadClick = { pdfPickerLauncher.launch("application/pdf") },
                        loading = loading
                    )
                }
                1 -> {
                    // Text Input Section
                    TextInputSection(
                        resumeText = resumeText,
                        onTextChange = { resumeText = it },
                        onAnalyzeClick = { viewModel.analyzeTextResume(resumeText) },
                        loading = loading
                    )
                }
            }

            Spacer(modifier = Modifier.height(24.dp))

            // Results Section
            if (state != null) {
                AnalysisResultsSection(result = state!!)
            }

            Spacer(modifier = Modifier.height(32.dp))
        }
    }
}

@Composable
fun PdfUploadSection(
    selectedFileName: String?,
    onUploadClick: () -> Unit,
    loading: Boolean
) {
    Column(
        modifier = Modifier.fillMaxWidth()
    ) {
        // Upload Box
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(200.dp)
                .background(NightSurface, RoundedCornerShape(16.dp))
                .border(2.dp, RedSecondary.copy(alpha = 0.3f), RoundedCornerShape(16.dp)),
            contentAlignment = Alignment.Center
        ) {
            Column(
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Icon(
                    imageVector = Icons.Default.Add,
                    contentDescription = "Upload",
                    tint = RedSecondary,
                    modifier = Modifier.size(48.dp)
                )
                Spacer(modifier = Modifier.height(16.dp))
                Text(
                    text = selectedFileName ?: "Upload PDF Resume",
                    fontSize = 16.sp,
                    fontFamily = poppins_regular,
                    color = if (selectedFileName != null) TextWhite else TextMediumGrey,
                    textAlign = TextAlign.Center
                )
            }
        }

        Spacer(modifier = Modifier.height(16.dp))

        Button(
            onClick = onUploadClick,
            modifier = Modifier
                .fillMaxWidth()
                .height(56.dp),
            shape = RoundedCornerShape(16.dp),
            colors = ButtonDefaults.buttonColors(
                containerColor = RedPrimary,
                contentColor = TextWhite
            ),
            enabled = !loading
        ) {
            if (loading) {
                CircularProgressIndicator(
                    modifier = Modifier.size(24.dp),
                    color = TextWhite
                )
            } else {
                Icon(
                    imageVector = Icons.Default.Send,
                    contentDescription = null,
                    modifier = Modifier.size(20.dp)
                )
                Spacer(modifier = Modifier.width(8.dp))
                Text(
                    text = "Select PDF File",
                    fontSize = 16.sp,
                    fontFamily = poppins_regular,
                    fontWeight = FontWeight.SemiBold
                )
            }
        }
    }
}

@Composable
fun TextInputSection(
    resumeText: String,
    onTextChange: (String) -> Unit,
    onAnalyzeClick: () -> Unit,
    loading: Boolean
) {
    Column(
        modifier = Modifier.fillMaxWidth()
    ) {
        Text(
            text = "Paste Your Resume Text",
            fontSize = 16.sp,
            fontFamily = poppins_regular,
            fontWeight = FontWeight.SemiBold,
            color = TextWhite,
            modifier = Modifier.padding(bottom = 8.dp)
        )

        OutlinedTextField(
            value = resumeText,
            onValueChange = onTextChange,
            placeholder = {
                Text(
                    text = "Paste your resume content here...",
                    color = TextMediumGrey
                )
            },
            modifier = Modifier
                .fillMaxWidth()
                .height(300.dp),
            shape = RoundedCornerShape(16.dp),
            colors = OutlinedTextFieldDefaults.colors(
                focusedContainerColor = NightSurface,
                unfocusedContainerColor = NightSurface,
                focusedBorderColor = RedSecondary,
                unfocusedBorderColor = Color.Transparent,
                focusedTextColor = TextWhite,
                unfocusedTextColor = TextWhite,
                cursorColor = RedSecondary
            )
        )

        Spacer(modifier = Modifier.height(16.dp))

        Button(
            onClick = onAnalyzeClick,
            modifier = Modifier
                .fillMaxWidth()
                .height(56.dp),
            shape = RoundedCornerShape(16.dp),
            colors = ButtonDefaults.buttonColors(
                containerColor = RedPrimary,
                contentColor = TextWhite
            ),
            enabled = resumeText.isNotBlank() && !loading
        ) {
            if (loading) {
                CircularProgressIndicator(
                    modifier = Modifier.size(24.dp),
                    color = TextWhite
                )
            } else {
                Icon(
                    imageVector = Icons.Default.Send,
                    contentDescription = null,
                    modifier = Modifier.size(20.dp)
                )
                Spacer(modifier = Modifier.width(8.dp))
                Text(
                    text = "Analyze Resume",
                    fontSize = 16.sp,
                    fontFamily = poppins_regular,
                    fontWeight = FontWeight.SemiBold
                )
            }
        }
    }
}

@Composable
fun AnalysisResultsSection(result: ResumeTextAnalysisModel) {
    Column(
        modifier = Modifier.fillMaxWidth()
    ) {
        Text(
            text = "Analysis Results",
            fontSize = 24.sp,
            fontFamily = poppins_regular,
            fontWeight = FontWeight.Bold,
            color = TextWhite
        )

        Spacer(modifier = Modifier.height(16.dp))

        // ATS Score Card
        ATSScoreCard(score = result.atsScore ?: 0)

        Spacer(modifier = Modifier.height(16.dp))

        // Skills Section
        if (!result.skills?.technical.isNullOrEmpty() || !result.skills?.soft.isNullOrEmpty()) {
            SkillsCard(skills = result.skills!!)
            Spacer(modifier = Modifier.height(16.dp))
        }

        // Strengths Section
        if (!result.strengths.isNullOrEmpty()) {
            SectionCard(
                title = "Strengths",
                items = result.strengths.filterNotNull(),
                icon = Icons.Default.Done,
                iconColor = SuccessGreen
            )
            Spacer(modifier = Modifier.height(16.dp))
        }

        // Weaknesses Section
        if (!result.weaknesses.isNullOrEmpty()) {
            SectionCard(
                title = "Weaknesses",
                items = result.weaknesses.filterNotNull(),
                icon = Icons.Default.Clear,
                iconColor = RedPrimary
            )
            Spacer(modifier = Modifier.height(16.dp))
        }

        // Missing Sections
        if (!result.missingSections.isNullOrEmpty()) {
            SectionCard(
                title = "Missing Sections",
                items = result.missingSections.filterNotNull(),
                icon = Icons.Default.Warning,
                iconColor = WarningYellow
            )
            Spacer(modifier = Modifier.height(16.dp))
        }

        // Improvement Suggestions
        if (!result.improvementSuggestions.isNullOrEmpty()) {
            SectionCard(
                title = "Improvement Suggestions",
                items = result.improvementSuggestions.filterNotNull(),
                icon = Icons.Default.Info,
                iconColor = RedSecondary
            )
        }
    }
}

@Composable
fun ATSScoreCard(score: Int) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(
            containerColor = NightSurface
        )
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(24.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = "ATS Score",
                fontSize = 18.sp,
                fontFamily = poppins_regular,
                fontWeight = FontWeight.SemiBold,
                color = TextLightGrey
            )

            Spacer(modifier = Modifier.height(12.dp))

            Text(
                text = "$score%",
                fontSize = 48.sp,
                fontFamily = poppins_regular,
                fontWeight = FontWeight.Bold,
                color = when {
                    score >= 80 -> SuccessGreen
                    score >= 60 -> WarningYellow
                    else -> RedPrimary
                }
            )

            Spacer(modifier = Modifier.height(8.dp))

            LinearProgressIndicator(
                progress = { score / 100f },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(8.dp),
                color = when {
                    score >= 80 -> SuccessGreen
                    score >= 60 -> WarningYellow
                    else -> RedPrimary
                },
                trackColor = TextMediumGrey.copy(alpha = 0.2f),
            )
        }
    }
}

@Composable
fun SkillsCard(skills: SkillsModel) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(
            containerColor = NightSurface
        )
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(20.dp)
        ) {
            Row(verticalAlignment = Alignment.CenterVertically) {
                Icon(
                    imageVector = Icons.Default.Build,
                    contentDescription = null,
                    tint = RedSecondary,
                    modifier = Modifier.size(24.dp)
                )
                Spacer(modifier = Modifier.width(8.dp))
                Text(
                    text = "Detected Skills",
                    fontSize = 18.sp,
                    fontFamily = poppins_regular,
                    fontWeight = FontWeight.Bold,
                    color = TextWhite
                )
            }

            if (!skills.technical.isNullOrEmpty()) {
                Spacer(modifier = Modifier.height(16.dp))
                Text(
                    text = "Technical Skills",
                    fontSize = 14.sp,
                    fontFamily = poppins_regular,
                    fontWeight = FontWeight.SemiBold,
                    color = TextLightGrey
                )
                Spacer(modifier = Modifier.height(8.dp))
                Text(
                    text = skills.technical.filterNotNull().joinToString(", "),
                    fontSize = 14.sp,
                    fontFamily = poppins_regular,
                    color = TextWhite,
                    lineHeight = 20.sp
                )
            }

            if (!skills.soft.isNullOrEmpty()) {
                Spacer(modifier = Modifier.height(12.dp))
                Text(
                    text = "Soft Skills",
                    fontSize = 14.sp,
                    fontFamily = poppins_regular,
                    fontWeight = FontWeight.SemiBold,
                    color = TextLightGrey
                )
                Spacer(modifier = Modifier.height(8.dp))
                Text(
                    text = skills.soft.filterNotNull().joinToString(", "),
                    fontSize = 14.sp,
                    fontFamily = poppins_regular,
                    color = TextWhite,
                    lineHeight = 20.sp
                )
            }
        }
    }
}

@Composable
fun SectionCard(
    title: String,
    items: List<String>,
    icon: ImageVector,
    iconColor: Color
) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(
            containerColor = NightSurface
        )
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(20.dp)
        ) {
            Row(verticalAlignment = Alignment.CenterVertically) {
                Icon(
                    imageVector = icon,
                    contentDescription = null,
                    tint = iconColor,
                    modifier = Modifier.size(24.dp)
                )
                Spacer(modifier = Modifier.width(8.dp))
                Text(
                    text = title,
                    fontSize = 18.sp,
                    fontFamily = poppins_regular,
                    fontWeight = FontWeight.Bold,
                    color = TextWhite
                )
            }

            Spacer(modifier = Modifier.height(12.dp))

            items.forEach { item ->
                Row(
                    modifier = Modifier.padding(vertical = 4.dp)
                ) {
                    Text(
                        text = "•",
                        fontSize = 14.sp,
                        color = iconColor,
                        modifier = Modifier.padding(end = 8.dp)
                    )
                    Text(
                        text = item,
                        fontSize = 14.sp,
                        fontFamily = poppins_regular,
                        color = TextWhite,
                        lineHeight = 20.sp,
                        modifier = Modifier.weight(1f)
                    )
                }
            }
        }
    }
}

@Preview
@Composable
fun ResumeAnalyzeScreenPreview() {
    HITO_3Theme {
        ResumeAnalyzeScreen()
    }
}