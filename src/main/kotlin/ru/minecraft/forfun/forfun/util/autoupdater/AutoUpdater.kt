package ru.minecraft.forfun.forfun.util.autoupdater

import net.minecraft.client.Minecraft
import net.minecraftforge.api.distmarker.Dist
import net.minecraftforge.api.distmarker.OnlyIn
import net.minecraftforge.fml.loading.FMLPaths
import org.apache.commons.io.FileUtils
import org.apache.logging.log4j.LogManager
import org.apache.logging.log4j.Logger
import ru.minecraft.forfun.forfun.Forfun.ID
import java.io.File
import java.io.IOException
import java.net.URL
import java.nio.file.Files
import java.nio.file.Path
import kotlin.io.path.Path
import org.apache.logging.log4j.Level
import kotlin.system.exitProcess

object AutoUpdater {
    val MCDIR: Path = FMLPaths.GAMEDIR.get()
    val version: URL = URL("https://raw.githubusercontent.com/cisster/forfun/refs/heads/master/version.txt")
    val file: File = File("$MCDIR\\mods\\forfun.jar")
    val DIRECTORY: Path = Path("$MCDIR\\forfun")
    val LOGGER: Logger = LogManager.getLogger(ID)

    fun downloadUpdate(): Boolean{
        val url: URL = URL("https://github.com/cisster/forfun/releases/download/alpha/forfun-${checkUpdate()}.jar")

        return runCatching {
            FileUtils.copyURLToFile(url, file)
            true
        }.onFailure { exception ->
            LOGGER.log(Level.ERROR, "Failed to download update for $url")
        }.getOrDefault(false)
    }
    fun checkUpdate(): String? {
        if (!Files.isDirectory(DIRECTORY)){
            Files.createDirectory(DIRECTORY)
        }
        return runCatching {
            FileUtils.copyURLToFile(version, File("$DIRECTORY\\version.txt"))
            val modVersion = Files.readString(Path("$DIRECTORY\\version.txt"))
            modVersion
            }.onFailure { exception ->
                LOGGER.log(Level.ERROR, "Failed to check version")
            }.getOrNull()
    }
    fun firstCreation(){
        if (!Files.isDirectory(DIRECTORY)){
            Files.createDirectory(DIRECTORY)
        }
        if (!Files.isReadable(Path("$DIRECTORY\\version.txt"))){
            Files.createFile(Path("$DIRECTORY\\version.txt"))
        }
    }
    fun checkShowScreen(): Boolean {
        val previousModVersion = Files.readString(Path("$DIRECTORY\\version.txt"))
        val modVersion = checkUpdate()
        return previousModVersion != modVersion
    }

    fun getUpdated(){
        checkUpdate()
        if (downloadUpdate()) {
            exitProcess(0)
        }
    }
}
