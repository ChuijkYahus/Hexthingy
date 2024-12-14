package me.uwutaku.hexthingy;

import at.petrak.hexcasting.api.casting.ActionRegistryEntry;
import at.petrak.hexcasting.api.casting.eval.CastingEnvironment;
import at.petrak.hexcasting.api.casting.math.HexDir;
import at.petrak.hexcasting.api.casting.math.HexPattern;
import at.petrak.hexcasting.common.lib.hex.HexActions;
import net.fabricmc.api.ModInitializer;
import net.minecraft.registry.Registry;
import net.minecraft.util.Identifier;

import java.util.Optional;

public class Hexthingy implements ModInitializer {

	public static final String MODID = "hexthingy";

	@Override
	public void onInitialize() {
		Registry.register(HexActions.REGISTRY, new Identifier(MODID, "smite"),
				new ActionRegistryEntry(
						HexPattern.fromAngles("wweeewwweeeqeeeadweeead", HexDir.EAST),
						new ActionPredicateWrapper(new Smite(),
								(CastingEnvironment env) -> Optional.empty()
						)
				)
		);
		Registry.register(HexActions.REGISTRY, new Identifier(MODID, "things/pasteurpurification"),
				new ActionRegistryEntry(
						HexPattern.fromAngles("aqqqqa", HexDir.NORTH_WEST),
						new ActionPredicateWrapper(new PasteurPurification(),
								(CastingEnvironment env) -> Optional.empty()
						)
				)
		);
		Registry.register(HexActions.REGISTRY, new Identifier(MODID, "things/cleaneffect"),
				new ActionRegistryEntry(
						HexPattern.fromAngles("qqqqwaqw", HexDir.SOUTH_WEST),
						new ActionPredicateWrapper(new CleanEffect(),
								(CastingEnvironment env) -> Optional.empty()
						)
				)
		);

	}
}