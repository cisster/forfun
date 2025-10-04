package ru.minecraft.forfun.forfun.particles

import net.minecraft.client.multiplayer.ClientLevel
import net.minecraft.client.particle.Particle
import net.minecraft.client.particle.ParticleProvider
import net.minecraft.client.particle.SpriteSet
import net.minecraft.core.particles.SimpleParticleType

class BoltParticleProvider(
    private val spriteSet: SpriteSet
): ParticleProvider<SimpleParticleType> {

    override fun createParticle(
        type: SimpleParticleType,
        level: ClientLevel,
        x: Double,
        y: Double,
        z: Double,
        xSpeed: Double,
        ySpeed: Double,
        zSpeed: Double
    ): Particle? {
        val critparticle = BoltParticle(level, x, y, z, xSpeed, ySpeed, zSpeed)
        critparticle.pickSprite(spriteSet)
        return critparticle
    }

}