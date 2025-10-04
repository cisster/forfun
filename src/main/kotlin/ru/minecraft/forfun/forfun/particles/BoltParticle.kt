package ru.minecraft.forfun.forfun.particles

import net.minecraft.client.multiplayer.ClientLevel
import net.minecraft.client.particle.ParticleRenderType
import net.minecraft.client.particle.TextureSheetParticle
import net.minecraft.util.Mth
import net.minecraftforge.api.distmarker.Dist
import net.minecraftforge.api.distmarker.OnlyIn
import kotlin.math.max

@OnlyIn(Dist.CLIENT)
class BoltParticle internal constructor(
    pLevel: ClientLevel,
    pX: Double,
    pY: Double,
    pZ: Double,
    pXSpeed: Double,
    pYSpeed: Double,
    pZSpeed: Double
) : TextureSheetParticle(pLevel, pX, pY, pZ, 0.0, 0.0, 0.0) {
    init {
        this.friction = 0.7f
        this.gravity = 0.5f
        this.xd *= 0.1
        this.yd *= 0.1
        this.zd *= 0.1
        this.xd += pXSpeed * 0.4
        this.yd += pYSpeed * 0.4
        this.zd += pZSpeed * 0.4
        val f = (Math.random() * 0.3 + 0.6).toFloat()
        this.rCol = f
        this.gCol = f
        this.bCol = f
        this.quadSize *= 0.75f
        this.lifetime = max((6.0 / (Math.random() * 0.8 + 0.6)).toInt(), 1)
        this.hasPhysics = false
        this.tick()
    }

    override fun getQuadSize(pScaleFactor: Float): Float {
        return this.quadSize * Mth.clamp(
            (this.age.toFloat() + pScaleFactor) / this.lifetime.toFloat() * 32.0f,
            0.0f,
            1.0f
        )
    }

    override fun tick() {
        super.tick()
        this.gCol *= 0.96f
        this.bCol *= 0.9f
    }

    override fun getRenderType(): ParticleRenderType {
        return ParticleRenderType.PARTICLE_SHEET_OPAQUE
    }
}