package ru.minecraft.forfun.forfun.renderer.civil_defence_armor

import ru.minecraft.forfun.forfun.geckolib.armor.CivilDefenceArmorItem
import software.bernie.geckolib.renderer.GeoArmorRenderer

class CivilDefenceArmorRenderer : GeoArmorRenderer<CivilDefenceArmorItem> {
	constructor() : super(
		CivilDefenceArmorModel
	)
}