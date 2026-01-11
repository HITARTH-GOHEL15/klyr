package com.example.hito_3.user_interface.sectionGenerator

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.selection.SelectionContainer
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.hito_3.data.resumeSectionGenerator.ResumeSectionGeneratorModel
import com.example.hito_3.ui.theme.*
import org.koin.androidx.compose.koinViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ResumeSectionGeneratorScreen(
    viewModel: ResumeSectionGeneratorViewModel = koinViewModel(),
    onBackClick: () -> Unit = {}
) {
    val state by viewModel.state.collectAsState()
    val loading by viewModel.loading.collectAsState()

    var selectedSectionType by remember { mutableStateOf("Summary") }
    var role by remember { mutableStateOf("") }
    var experienceLevel by remember { mutableStateOf("") }
    var skills by remember { mutableStateOf("") }
    var showSectionTypeMenu by remember { mutableStateOf(false) }
    var showExperienceMenu by remember { mutableStateOf(false) }

    val sectionTypes = listOf(
        "Summary",
        "Experience",
        "Skills",
        "Education",
        "Projects",
        "Certifications"
    )

    val experienceLevels = listOf(
        "Entry Level",
        "Junior",
        "Mid Level",
        "Senior",
        "Lead",
        "Executive"
    )

    Scaffold(
        containerColor = NightBlack,
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        text = "Section Generator",
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

            // Section Type Dropdown
            Column(modifier = Modifier.fillMaxWidth()) {
                Text(
                    text = "Section Type",
                    fontSize = 16.sp,
                    fontFamily = poppins_regular,
                    fontWeight = FontWeight.SemiBold,
                    color = TextWhite,
                    modifier = Modifier.padding(bottom = 8.dp)
                )

                ExposedDropdownMenuBox(
                    expanded = showSectionTypeMenu,
                    onExpandedChange = { showSectionTypeMenu = it }
                ) {
                    OutlinedTextField(
                        value = selectedSectionType,
                        onValueChange = {},
                        readOnly = true,
                        trailingIcon = {
                            Icon(
                                imageVector = if (showSectionTypeMenu)
                                    Icons.Default.KeyboardArrowUp
                                else
                                    Icons.Default.KeyboardArrowDown,
                                contentDescription = null,
                                tint = TextWhite
                            )
                        },
                        modifier = Modifier
                            .menuAnchor()
                            .fillMaxWidth(),
                        shape = RoundedCornerShape(16.dp),
                        colors = OutlinedTextFieldDefaults.colors(
                            focusedContainerColor = NightSurface,
                            unfocusedContainerColor = NightSurface,
                            focusedBorderColor = RedSecondary,
                            unfocusedBorderColor = Color.Transparent,
                            focusedTextColor = TextWhite,
                            unfocusedTextColor = TextWhite
                        )
                    )

                    ExposedDropdownMenu(
                        expanded = showSectionTypeMenu,
                        onDismissRequest = { showSectionTypeMenu = false },
                        modifier = Modifier.background(NightSurface)
                    ) {
                        sectionTypes.forEach { section ->
                            DropdownMenuItem(
                                text = {
                                    Text(
                                        text = section,
                                        color = TextWhite,
                                        fontFamily = poppins_regular
                                    )
                                },
                                onClick = {
                                    selectedSectionType = section
                                    showSectionTypeMenu = false
                                }
                            )
                        }
                    }
                }
            }

            Spacer(modifier = Modifier.height(20.dp))

            // Role Input
            Column(modifier = Modifier.fillMaxWidth()) {
                Text(
                    text = "Target Role",
                    fontSize = 16.sp,
                    fontFamily = poppins_regular,
                    fontWeight = FontWeight.SemiBold,
                    color = TextWhite,
                    modifier = Modifier.padding(bottom = 8.dp)
                )

                OutlinedTextField(
                    value = role,
                    onValueChange = { role = it },
                    placeholder = {
                        Text(
                            text = "e.g., Senior Software Engineer",
                            color = TextMediumGrey
                        )
                    },
                    modifier = Modifier.fillMaxWidth(),
                    shape = RoundedCornerShape(16.dp),
                    colors = OutlinedTextFieldDefaults.colors(
                        focusedContainerColor = NightSurface,
                        unfocusedContainerColor = NightSurface,
                        focusedBorderColor = RedSecondary,
                        unfocusedBorderColor = Color.Transparent,
                        focusedTextColor = TextWhite,
                        unfocusedTextColor = TextWhite,
                        cursorColor = RedSecondary
                    ),
                    singleLine = true
                )
            }

            Spacer(modifier = Modifier.height(20.dp))

            // Experience Level Dropdown
            Column(modifier = Modifier.fillMaxWidth()) {
                Text(
                    text = "Experience Level",
                    fontSize = 16.sp,
                    fontFamily = poppins_regular,
                    fontWeight = FontWeight.SemiBold,
                    color = TextWhite,
                    modifier = Modifier.padding(bottom = 8.dp)
                )

                ExposedDropdownMenuBox(
                    expanded = showExperienceMenu,
                    onExpandedChange = { showExperienceMenu = it }
                ) {
                    OutlinedTextField(
                        value = experienceLevel,
                        onValueChange = {},
                        readOnly = true,
                        placeholder = {
                            Text(
                                text = "Select experience level",
                                color = TextMediumGrey
                            )
                        },
                        trailingIcon = {
                            Icon(
                                imageVector = if (showExperienceMenu)
                                    Icons.Default.KeyboardArrowUp
                                else
                                    Icons.Default.KeyboardArrowDown,
                                contentDescription = null,
                                tint = TextWhite
                            )
                        },
                        modifier = Modifier
                            .menuAnchor()
                            .fillMaxWidth(),
                        shape = RoundedCornerShape(16.dp),
                        colors = OutlinedTextFieldDefaults.colors(
                            focusedContainerColor = NightSurface,
                            unfocusedContainerColor = NightSurface,
                            focusedBorderColor = RedSecondary,
                            unfocusedBorderColor = Color.Transparent,
                            focusedTextColor = TextWhite,
                            unfocusedTextColor = TextWhite
                        )
                    )

                    ExposedDropdownMenu(
                        expanded = showExperienceMenu,
                        onDismissRequest = { showExperienceMenu = false },
                        modifier = Modifier.background(NightSurface)
                    ) {
                        experienceLevels.forEach { level ->
                            DropdownMenuItem(
                                text = {
                                    Text(
                                        text = level,
                                        color = TextWhite,
                                        fontFamily = poppins_regular
                                    )
                                },
                                onClick = {
                                    experienceLevel = level
                                    showExperienceMenu = false
                                }
                            )
                        }
                    }
                }
            }

            Spacer(modifier = Modifier.height(20.dp))

            // Skills Input
            Column(modifier = Modifier.fillMaxWidth()) {
                Text(
                    text = "Key Skills",
                    fontSize = 16.sp,
                    fontFamily = poppins_regular,
                    fontWeight = FontWeight.SemiBold,
                    color = TextWhite,
                    modifier = Modifier.padding(bottom = 8.dp)
                )

                OutlinedTextField(
                    value = skills,
                    onValueChange = { skills = it },
                    placeholder = {
                        Text(
                            text = "e.g., Python, React, AWS, Docker",
                            color = TextMediumGrey
                        )
                    },
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(100.dp),
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

                Spacer(modifier = Modifier.height(6.dp))

                Text(
                    text = "Separate skills with commas",
                    fontSize = 12.sp,
                    fontFamily = poppins_regular,
                    color = TextMediumGrey
                )
            }

            Spacer(modifier = Modifier.height(24.dp))

            // Generate Button
            Button(
                onClick = {
                    val skillsList = skills
                        .split(",")
                        .map { it.trim() }
                        .filter { it.isNotBlank() }
                    viewModel.ResumeSectionGenerator(
                        selectedSectionType,
                        role,
                        experienceLevel,
                        skillsList
                    )
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(56.dp),
                shape = RoundedCornerShape(16.dp),
                colors = ButtonDefaults.buttonColors(
                    containerColor = RedPrimary,
                    contentColor = TextWhite
                ),
                enabled = role.isNotBlank() &&
                        experienceLevel.isNotBlank() &&
                        skills.isNotBlank() &&
                        !loading
            ) {
                if (loading) {
                    CircularProgressIndicator(
                        modifier = Modifier.size(24.dp),
                        color = TextWhite
                    )
                } else {
                    Icon(
                        imageVector = Icons.Default.Create,
                        contentDescription = null,
                        modifier = Modifier.size(20.dp)
                    )
                    Spacer(modifier = Modifier.width(8.dp))
                    Text(
                        text = "Generate Section",
                        fontSize = 16.sp,
                        fontFamily = poppins_regular,
                        fontWeight = FontWeight.SemiBold
                    )
                }
            }

            Spacer(modifier = Modifier.height(24.dp))

            // Results Section
            if (state != null) {
                GeneratedSectionResult(result = state!!)
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
                text = "Generate professional resume sections tailored to your target role and experience level",
                fontSize = 13.sp,
                fontFamily = poppins_regular,
                color = TextLightGrey,
                lineHeight = 18.sp
            )
        }
    }
}

@Composable
fun GeneratedSectionResult(result: ResumeSectionGeneratorModel) {
    Column(modifier = Modifier.fillMaxWidth()) {
        Text(
            text = "Generated Section",
            fontSize = 24.sp,
            fontFamily = poppins_regular,
            fontWeight = FontWeight.Bold,
            color = TextWhite
        )

        Spacer(modifier = Modifier.height(16.dp))

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
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Icon(
                            imageVector = Icons.Default.Check,
                            contentDescription = null,
                            tint = SuccessGreen,
                            modifier = Modifier.size(24.dp)
                        )
                        Spacer(modifier = Modifier.width(8.dp))
                        Text(
                            text = "Ready to Use",
                            fontSize = 18.sp,
                            fontFamily = poppins_regular,
                            fontWeight = FontWeight.Bold,
                            color = TextWhite
                        )
                    }

                    // Copy Button
                    IconButton(
                        onClick = {
                            // TODO: Copy to clipboard
                        }
                    ) {
                        Icon(
                            imageVector = Icons.Default.Share,
                            contentDescription = "Copy",
                            tint = RedSecondary
                        )
                    }
                }

                Spacer(modifier = Modifier.height(16.dp))

                SelectionContainer {
                    Text(
                        text = result.generatedSection,
                        fontSize = 14.sp,
                        fontFamily = poppins_regular,
                        color = TextWhite,
                        lineHeight = 22.sp
                    )
                }

                if (result.analysisMethod.isNotBlank()) {
                    Spacer(modifier = Modifier.height(12.dp))

                    Text(
                        text = "Method: ${result.analysisMethod}",
                        fontSize = 12.sp,
                        fontFamily = poppins_regular,
                        color = TextMediumGrey
                    )
                }
            }
        }
    }
}

@Preview
@Composable
fun ResumeSectionGeneratorScreenPreview() {
    HITO_3Theme {
        ResumeSectionGeneratorScreen()
    }
}