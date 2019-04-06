package com.androidacademy.msk.exerciseproject.utils

import com.androidacademy.msk.exerciseproject.model.Section


object SectionUtils {

    fun getSection(name: String): Section = Section.valueOf(name.toUpperCase())

    fun getSectionForQuery(section: Section): String = section.name.toLowerCase()

    fun getCapitalizedSectionName(section: Section) : String = section.name.toLowerCase().capitalize()

}