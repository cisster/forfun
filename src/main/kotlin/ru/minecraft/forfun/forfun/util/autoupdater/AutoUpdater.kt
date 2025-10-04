package ru.minecraft.forfun.forfun.util.autoupdater

import net.minecraftforge.fml.loading.FMLPaths
import org.apache.commons.io.FileUtils
import java.io.File
import java.io.FileReader
import java.net.URL
import java.nio.file.Files
import java.nio.file.Path

object AutoUpdater {
    val mcDir: Path = FMLPaths.GAMEDIR.get()
    val url: URL = URL("https://github.com/cisster/forfun/releases/download/alpha/forfun-0.0.1.jar")
    val file: File = File("$mcDir\\mods\\forfun.jar")
    fun downloadFile(){
        FileUtils.copyURLToFile(url, file)
    }
}