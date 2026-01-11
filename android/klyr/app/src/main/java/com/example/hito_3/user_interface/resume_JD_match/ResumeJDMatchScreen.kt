package com.example.hito_3.user_interface.resume_JD_match



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
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.hito_3.data.resumeMatch.LearningRecommendationModel
import com.example.hito_3.data.resumeMatch.Resume_JD_matchModel
import com.example.hito_3.ui.theme.*
import org.koin.androidx.compose.koinViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ResumeJDMatchScreen(
    viewModel: ResumeJDMatchViewModel = koinViewModel(),
    onBackClick: () -> Unit = {}
) {
    val state by viewModel.state.collectAsState()
    val loading by viewModel.loading.collectAsState()

    var resumeText by remember { mutableStateOf("") }
    var jobDescription by remember { mutableStateOf("") }

    Scaffold(
        containerColor = NightBlack,
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        text = "Resume-JD Match",
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

            // Info Card
            InfoCard()

            Spacer(modifier = Modifier.height(24.dp))

            // Resume Text Input
            ResumeInputSection(
                resumeText = resumeText,
                onTextChange = { resumeText = it }
            )

            Spacer(modifier = Modifier.height(20.dp))

            // Job Description Input
            JobDescriptionSection(
                jobDescription = jobDescription,
                onTextChange = { jobDescription = it }
            )

            Spacer(modifier = Modifier.height(24.dp))

            // Analyze Button
            Button(
                onClick = {
                    viewModel.ResumeJDMatch(resumeText, jobDescription)
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(56.dp),
                shape = RoundedCornerShape(16.dp),
                colors = ButtonDefaults.buttonColors(
                    containerColor = RedPrimary,
                    contentColor = TextWhite
                ),
                enabled = resumeText.isNotBlank() && jobDescription.isNotBlank() && !loading
            ) {
                if (loading) {
                    CircularProgressIndicator(
                        modifier = Modifier.size(24.dp),
                        color = TextWhite
                    )
                } else {
                    Icon(
                        imageVector = Icons.Default.Check,
                        contentDescription = null,
                        modifier = Modifier.size(20.dp)
                    )
                    Spacer(modifier = Modifier.width(8.dp))
                    Text(
                        text = "Check Match",
                        fontSize = 16.sp,
                        fontFamily = poppins_regular,
                        fontWeight = FontWeight.SemiBold
                    )
                }
            }

            Spacer(modifier = Modifier.height(24.dp))

            // Results Section
            if (state != null) {
                MatchResultsSection(result = state!!)
            }

            Spacer(modifier = Modifier.height(32.dp))
        }
    }
}

@Composable
fun InfoCard() {
    Card(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(
            containerColor = NightSurface
        )
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Icon(
                imageVector = Icons.Default.Info,
                contentDescription = null,
                tint = RedSecondary,
                modifier = Modifier.size(24.dp)
            )
            Spacer(modifier = Modifier.width(12.dp))
            Text(
                text = "Compare your resume with a job description to see how well you match and get recommendations",
                fontSize = 13.sp,
                fontFamily = poppins_regular,
                color = TextLightGrey,
                lineHeight = 18.sp
            )
        }
    }
}

@Composable
fun ResumeInputSection(
    resumeText: String,
    onTextChange: (String) -> Unit
) {
    Column(
        modifier = Modifier.fillMaxWidth()
    ) {
        Text(
            text = "Your Resume",
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
                .height(200.dp),
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
    }
}

@Composable
fun JobDescriptionSection(
    jobDescription: String,
    onTextChange: (String) -> Unit
) {
    Column(
        modifier = Modifier.fillMaxWidth()
    ) {
        Text(
            text = "Job Description",
            fontSize = 16.sp,
            fontFamily = poppins_regular,
            fontWeight = FontWeight.SemiBold,
            color = TextWhite,
            modifier = Modifier.padding(bottom = 8.dp)
        )

        OutlinedTextField(
            value = jobDescription,
            onValueChange = onTextChange,
            placeholder = {
                Text(
                    text = "Paste the job description here...",
                    color = TextMediumGrey
                )
            },
            modifier = Modifier
                .fillMaxWidth()
                .height(200.dp),
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
    }
}

@Composable
fun MatchResultsSection(result: Resume_JD_matchModel) {
    Column(
        modifier = Modifier.fillMaxWidth()
    ) {
        Text(
            text = "Match Results",
            fontSize = 24.sp,
            fontFamily = poppins_regular,
            fontWeight = FontWeight.Bold,
            color = TextWhite
        )

        Spacer(modifier = Modifier.height(16.dp))

        // Match Percentage Card
        MatchPercentageCard(percentage = result.matchPercentage)

        Spacer(modifier = Modifier.height(16.dp))

        // Summary
        if (result.skillGapSummary.isNotBlank()) {
            SummaryCard(summary = result.skillGapSummary)
            Spacer(modifier = Modifier.height(16.dp))
        }

        // Matched Skills
        if (result.matchedSkills.isNotEmpty()) {
            SkillsListCard(
                title = "Matched Skills",
                skills = result.matchedSkills,
                icon = Icons.Default.Check,
                iconColor = SuccessGreen
            )
            Spacer(modifier = Modifier.height(16.dp))
        }

        // Missing Skills
        if (result.missingSkills.isNotEmpty()) {
            SkillsListCard(
                title = "Missing Skills",
                skills = result.missingSkills,
                icon = Icons.Default.Clear,
                iconColor = RedPrimary
            )
            Spacer(modifier = Modifier.height(16.dp))
        }

        // Learning Recommendations
        if (result.learningRecommendations.isNotEmpty()) {
            LearningRecommendationsSection(
                recommendations = result.learningRecommendations
            )
        }
    }
}

@Composable
fun MatchPercentageCard(percentage: Int) {
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
                text = "Match Score",
                fontSize = 18.sp,
                fontFamily = poppins_regular,
                fontWeight = FontWeight.SemiBold,
                color = TextLightGrey
            )

            Spacer(modifier = Modifier.height(12.dp))

            Text(
                text = "$percentage%",
                fontSize = 48.sp,
                fontFamily = poppins_regular,
                fontWeight = FontWeight.Bold,
                color = when {
                    percentage >= 80 -> SuccessGreen
                    percentage >= 60 -> WarningYellow
                    else -> RedPrimary
                }
            )

            Spacer(modifier = Modifier.height(8.dp))

            LinearProgressIndicator(
                progress = { percentage / 100f },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(8.dp),
                color = when {
                    percentage >= 80 -> SuccessGreen
                    percentage >= 60 -> WarningYellow
                    else -> RedPrimary
                },
                trackColor = TextMediumGrey.copy(alpha = 0.2f),
            )
        }
    }
}

@Composable
fun SummaryCard(summary: String) {
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
                    imageVector = Icons.Default.Info,
                    contentDescription = null,
                    tint = RedSecondary,
                    modifier = Modifier.size(24.dp)
                )
                Spacer(modifier = Modifier.width(8.dp))
                Text(
                    text = "Summary",
                    fontSize = 18.sp,
                    fontFamily = poppins_regular,
                    fontWeight = FontWeight.Bold,
                    color = TextWhite
                )
            }

            Spacer(modifier = Modifier.height(12.dp))

            Text(
                text = summary,
                fontSize = 14.sp,
                fontFamily = poppins_regular,
                color = TextWhite,
                lineHeight = 20.sp
            )
        }
    }
}

@OptIn(ExperimentalLayoutApi::class)
@Composable
fun SkillsListCard(
    title: String,
    skills: List<String>,
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

            // Simple list with bullet points instead of chips
            skills.forEach { skill ->
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
                        text = skill,
                        fontSize = 14.sp,
                        fontFamily = poppins_regular,
                        color = TextWhite,
                        lineHeight = 20.sp
                    )
                }
            }
        }
    }
}

@Composable
fun SkillChip(skill: String, color: Color) {
    Box(
        modifier = Modifier
            .background(
                color = color.copy(alpha = 0.15f),
                shape = RoundedCornerShape(20.dp)
            )
            .border(
                width = 1.dp,
                color = color.copy(alpha = 0.3f),
                shape = RoundedCornerShape(20.dp)
            )
            .padding(horizontal = 12.dp, vertical = 6.dp)
    ) {
        Text(
            text = skill,
            fontSize = 13.sp,
            fontFamily = poppins_regular,
            color = TextWhite
        )
    }
}

@Composable
fun LearningRecommendationsSection(
    recommendations: List<LearningRecommendationModel>
) {
    Text(
        text = "Recommendations",
        fontSize = 20.sp,
        fontFamily = poppins_regular,
        fontWeight = FontWeight.Bold,
        color = TextWhite
    )

    Spacer(modifier = Modifier.height(12.dp))

    recommendations.forEach { recommendation ->
        LearningRecommendationCard(recommendation)
        Spacer(modifier = Modifier.height(12.dp))
    }
}

@Composable
fun LearningRecommendationCard(recommendation: LearningRecommendationModel) {
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
                    imageVector = Icons.Default.Star,
                    contentDescription = null,
                    tint = WarningYellow,
                    modifier = Modifier.size(24.dp)
                )
                Spacer(modifier = Modifier.width(8.dp))
                Text(
                    text = recommendation.skill,
                    fontSize = 18.sp,
                    fontFamily = poppins_regular,
                    fontWeight = FontWeight.Bold,
                    color = TextWhite
                )
            }

            Spacer(modifier = Modifier.height(16.dp))

            Column {
                Text(
                    text = "Why You Need This",
                    fontSize = 14.sp,
                    fontFamily = poppins_regular,
                    fontWeight = FontWeight.SemiBold,
                    color = RedSecondary
                )
                Spacer(modifier = Modifier.height(6.dp))
                Text(
                    text = recommendation.whyNeeded,
                    fontSize = 13.sp,
                    fontFamily = poppins_regular,
                    color = TextLightGrey,
                    lineHeight = 18.sp
                )
            }

            Spacer(modifier = Modifier.height(12.dp))

            Column {
                Text(
                    text = "How to Learn",
                    fontSize = 14.sp,
                    fontFamily = poppins_regular,
                    fontWeight = FontWeight.SemiBold,
                    color = SuccessGreen
                )
                Spacer(modifier = Modifier.height(6.dp))
                Text(
                    text = recommendation.howToLearn,
                    fontSize = 13.sp,
                    fontFamily = poppins_regular,
                    color = TextLightGrey,
                    lineHeight = 18.sp
                )
            }
        }
    }
}

@Preview
@Composable
fun ResumeJDMatchScreenPreview() {
    HITO_3Theme {
        ResumeJDMatchScreen()
    }
}