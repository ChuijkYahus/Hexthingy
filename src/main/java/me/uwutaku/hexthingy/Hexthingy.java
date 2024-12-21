package me.uwutaku.hexthingy;

import at.petrak.hexcasting.api.addldata.ADMediaHolder;
import at.petrak.hexcasting.api.casting.ActionRegistryEntry;
import at.petrak.hexcasting.api.casting.castables.Action;
import at.petrak.hexcasting.api.casting.eval.CastingEnvironment;
import at.petrak.hexcasting.api.casting.math.HexDir;
import at.petrak.hexcasting.api.casting.math.HexPattern;
import at.petrak.hexcasting.api.utils.MediaHelper;
import at.petrak.hexcasting.common.lib.hex.HexActions;
import net.fabricmc.api.ModInitializer;
import net.minecraft.entity.LivingEntity;
import net.minecraft.registry.Registry;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.util.Identifier;

import java.util.List;
import java.util.Optional;

public class Hexthingy implements ModInitializer {

	public static final String MODID = "hexthingy";

	@Override
	public void onInitialize() {
		registerAction("smite", "wweeewwweeeqeeeadweeead", HexDir.EAST, new Smite());
		registerAction("things/pasteurpurification", "aqqqqa", HexDir.NORTH_WEST, new PasteurPurification());
		registerAction("things/cleaneffect", "qqqqwaqw", HexDir.SOUTH_WEST, new CleanEffect());
		registerAction("allaycreation", "qqqqqweeweeaeeqeeeeeweeweeaeeaeeweedqqqqdadedaddwwqqq", HexDir.SOUTH_EAST, new AllayCreation());

	}
	private void registerAction(String actionId, String pattern, HexDir direction, Action action) {
		Registry.register(
				HexActions.REGISTRY,
				new Identifier(MODID, actionId),
				new ActionRegistryEntry(
						HexPattern.fromAngles(pattern, direction),
						new ActionPredicateWrapper(action, (CastingEnvironment env) -> Optional.empty())
				)
		);
	}
	public static float getTotalMediaFromEntity(LivingEntity entity){
		if(!entity.isPlayer()){
			return 0.0f;
		}
		float media = 0.0f;
		List<ADMediaHolder> media_holders = MediaHelper.scanPlayerForMediaStuff((ServerPlayerEntity) entity);
		for (ADMediaHolder holder : media_holders){
			media += holder.getMedia();
		}
		return media;
	}

}