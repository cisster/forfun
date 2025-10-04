package ru.minecraft.forfun.forfun.registry

import net.minecraft.world.entity.EntityType
import net.minecraft.world.entity.Mob
import net.minecraft.world.entity.MobCategory
import net.minecraftforge.registries.DeferredRegister
import net.minecraftforge.registries.ForgeRegistries
import net.minecraftforge.registries.RegistryObject
import ru.minecraft.forfun.forfun.Forfun
import ru.minecraft.forfun.forfun.geckolib.entity.DroneEntity


object  EntityRegistry {
    val ENTITIES: DeferredRegister<EntityType<*>> = DeferredRegister.create(ForgeRegistries.ENTITY_TYPES,
        Forfun.ID)

    val DRONE: RegistryObject<EntityType<DroneEntity>> = registerMob("drone", ::DroneEntity, 1.0f, 1.0f, 0x1F1F1F, 0x0D0D0D)




    fun <T : Mob> registerMob(
        name: String,
        entity: EntityType.EntityFactory<T>,
        width: Float,
        height: Float,
        primaryEggColor: Int,
        secondaryEggColor: Int
    ): RegistryObject<EntityType<T>> {
        val entityType = ENTITIES.register(name) {
            EntityType.Builder.of(entity, MobCategory.CREATURE)
                .sized(width, height)
                .build(name)
        }

        return entityType
    }
}
