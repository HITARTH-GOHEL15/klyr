package com.example.hito_3.user_interface.dashboard

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Build
import androidx.compose.material.icons.filled.Create
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material.icons.filled.Notifications
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material.icons.outlined.Notifications
import androidx.compose.material.icons.sharp.AccountBox
import androidx.compose.material.icons.sharp.Person
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.hito_3.ui.theme.NightSurface
import com.example.hito_3.ui.theme.RedSecondary
import com.example.hito_3.ui.theme.SuccessGreen
import com.example.hito_3.ui.theme.TextLightGrey
import com.example.hito_3.ui.theme.TextMediumGrey
import com.example.hito_3.ui.theme.TextWhite
import com.example.hito_3.ui.theme.poppins_bold
import com.example.hito_3.ui.theme.poppins_extraBold
import com.example.hito_3.ui.theme.poppins_medium
import com.example.hito_3.ui.theme.poppins_regular

@Composable
fun DashboardScreen(
    padding: PaddingValues,
    onAnalyzeClick: () -> Unit,
    onSkillGapClick: () -> Unit,
    onSectionGenClick: () -> Unit,
    onResumeJDMatchClick: () -> Unit,
    onBulletRewriterClick: () -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(padding)
            .verticalScroll(rememberScrollState())
            .padding(horizontal = 20.dp)
    ) {
        Spacer(modifier = Modifier.height(16.dp))

        // Header Section
        HeaderSection()

        Spacer(modifier = Modifier.height(32.dp))

        // Feature Cards Grid
        FeatureCardsGrid(
            onAnalyzeClick = onAnalyzeClick,
            onSkillGapClick = onSkillGapClick,
            onSectionGenClick = onSectionGenClick,
            onResumeJDMatchClick = onResumeJDMatchClick
        )

        Spacer(modifier = Modifier.height(24.dp))


        AIResumeBulletRewriterCard(
            onBulletRewriterClick
        )

        Spacer(modifier = Modifier.height(32.dp))

        // Stats Section
        StatsSection()

        Spacer(modifier = Modifier.height(24.dp))
    }
}


@Composable
fun HeaderSection() {
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Row(verticalAlignment = Alignment.CenterVertically) {

            Spacer(modifier = Modifier.width(16.dp))

            Text(
                text = "Klyr",
                fontSize = 30.sp,
                fontFamily = poppins_medium,
                fontWeight = FontWeight.Bold,
                color = TextWhite
            )
        }

    }
}

@Composable
fun FeatureCardsGrid(
    onAnalyzeClick: () -> Unit,
    onSkillGapClick: () -> Unit,
    onSectionGenClick: () -> Unit,
    onResumeJDMatchClick: () -> Unit
) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        FeatureCard(
            icon = Icons.Default.Edit,
            title = "AI Resume Analysis",
            subtitle = "Check your score",
            modifier = Modifier.weight(1f),
            onClick = onAnalyzeClick
        )

        FeatureCard(
            icon = Icons.Default.Add,
            title = "Resume-JD Match",
            subtitle = "Compare with job posts",
            modifier = Modifier.weight(1f),
            onClick = onResumeJDMatchClick
        )

    }

    Spacer(modifier = Modifier.height(16.dp))

    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        FeatureCard(
            icon = Icons.Default.Settings,
            title = "Section Generator",
            subtitle = "Wrtie sections with AI",
            modifier = Modifier.weight(1f),
            onClick = onSectionGenClick
        )

        FeatureCard(
            icon = Icons.Default.Build,
            title = "Skill Gap Report",
            subtitle = "Find skills to develop",
            modifier = Modifier.weight(1f),
            onClick = onSkillGapClick
        )
    }
}

@Composable
fun FeatureCard(
    icon: ImageVector,
    title: String,
    subtitle: String,
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Card(
        modifier = modifier.height(140.dp),
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(
            containerColor = NightSurface
        ),
        onClick = onClick
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp),
            verticalArrangement = Arrangement.SpaceBetween
        ) {
            Icon(
                imageVector = icon,
                contentDescription = title,
                tint = RedSecondary,
                modifier = Modifier.size(32.dp)
            )

            Column {
                Text(
                    text = title,
                    fontSize = 16.sp,
                    fontFamily = poppins_medium,
                    fontWeight = FontWeight.Bold,
                    color = TextWhite
                )

                Spacer(modifier = Modifier.height(4.dp))

                Text(
                    text = subtitle,
                    fontSize = 11.sp,
                    fontFamily = poppins_regular,
                    color = TextLightGrey,
                    lineHeight = 18.sp
                )
            }
        }
    }
}

@Composable
fun AIResumeBulletRewriterCard(
    onBulletRewriterClick: () -> Unit
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .height(120.dp),
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(
            containerColor = NightSurface
        ),
        onClick = onBulletRewriterClick
    ) {
        Row(
            modifier = Modifier
                .fillMaxSize()
                .padding(20.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Icon(
                imageVector = Icons.Default.Create,
                contentDescription = "Bullet Rewriter",
                tint = RedSecondary,
                modifier = Modifier.size(36.dp)
            )

            Spacer(modifier = Modifier.width(20.dp))

            Column {
                Text(
                    text = "Bullet Rewriter",
                    fontSize = 18.sp,
                    fontFamily = poppins_medium,
                    fontWeight = FontWeight.Bold,
                    color = TextWhite
                )

                Spacer(modifier = Modifier.height(6.dp))

                Text(
                    text = "Enhance your points",
                    fontFamily = poppins_regular,
                    fontSize = 14.sp,
                    color = TextLightGrey
                )
            }
        }
    }
}

@Composable
fun StatsSection() {
    Text(
        text = "Your Stats",
        fontSize = 24.sp,
        fontFamily = poppins_medium,
        fontWeight = FontWeight.Bold,
        color = TextWhite
    )

    Spacer(modifier = Modifier.height(16.dp))

    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        StatCard(
            label = "Last ATS Score",
            value = "88%",
            change = "+5%",
            modifier = Modifier.weight(1f)
        )

        StatCard(
            label = "Last Updated",
            value = "Oct 26",
            change = null,
            modifier = Modifier.weight(1f)
        )
    }
}

@Composable
fun StatCard(
    label: String,
    value: String,
    change: String?,
    modifier: Modifier = Modifier
) {
    Card(
        modifier = modifier.height(140.dp),
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(
            containerColor = NightSurface
        )
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(20.dp),
            verticalArrangement = Arrangement.Center
        ) {
            Text(
                text = label,
                fontFamily = poppins_regular,
                fontSize = 14.sp,
                color = TextMediumGrey
            )

            Spacer(modifier = Modifier.height(12.dp))

            Text(
                text = value,
                fontSize = 26.sp,
                fontFamily = poppins_medium,
                fontWeight = FontWeight.Bold,
                color = TextWhite
            )

            if (change != null) {
                Spacer(modifier = Modifier.height(6.dp))

                Text(
                    text = change,
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Medium,
                    color = SuccessGreen
                )
            }
        }
    }
}