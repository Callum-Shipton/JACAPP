package display;

public class Art extends ArtLoader {

	@Override
	public void loadArt() {

		// load menu screen art
		addArt("MainMenuScreen", new Image("/Images/Menus/Backgrounds/MainMenuScreen.png", 1, 1));
		addArt("InventoryScreen", new Image("/Images/Menus/Backgrounds/InventoryScreen.png", 1, 1));
		addArt("SkillScreen", new Image("/Images/Menus/Backgrounds/SkillScreen.png", 1, 1));
		addArt("UpgradesScreen", new Image("/Images/Menus/Backgrounds/UpgradesScreen.png", 1, 1));
		addArt("MapScreen", new Image("/Images/Menus/Backgrounds/MapScreen.png", 1, 1));
		addArt("SaveScreen", new Image("/Images/Menus/Backgrounds/SaveScreen.png", 1, 1));
		addArt("GameOverScreen", new Image("/Images/Menus/Backgrounds/GameOverScreen.png", 1, 1));

		// load button art
		addArt("NewGameButton", new Image("/Images/Menus/Buttons/MenuButtons/NewGameButton.png", 1, 2));

		addArt("LoadGameButton",
				new Image("/Images/Menus/Buttons/MenuButtons/LoadGameButton.png", 1, 2));
		addArt("OptionsButton", new Image("/Images/Menus/Buttons/MenuButtons/OptionsButton.png", 1, 2));
		addArt("ExitButton", new Image("/Images/Menus/Buttons/MenuButtons/ExitButton.png", 1, 2));

		addArt("BackButton", new Image("/Images/Menus/Buttons/MenuButtons/BackButton.png", 1, 2));

		addArt("ControlsButton",
				new Image("/Images/Menus/Buttons/MenuButtons/ControlsButton.png", 1, 2));
		addArt("SoundButton", new Image("/Images/Menus/Buttons/MenuButtons/SoundButton.png", 1, 2));

		addArt("MuteButton", new Image("/Images/Menus/Buttons/MenuButtons/MuteButton.png", 1, 2));

		addArt("SaveGameButton",
				new Image("/Images/Menus/Buttons/MenuButtons/SaveGameButton.png", 1, 2));

		addArt("Save1Button", new Image("/Images/Menus/Buttons/MenuButtons/Save1Button.png", 1, 2));
		addArt("Save2Button", new Image("/Images/Menus/Buttons/MenuButtons/Save2Button.png", 1, 2));
		addArt("Save3Button", new Image("/Images/Menus/Buttons/MenuButtons/Save3Button.png", 1, 2));

		addArt("HealthButton", new Image("/Images/Menus/Buttons/MenuButtons/HealthButton.png", 1, 2));
		addArt("HealthRegenButton",
				new Image("/Images/Menus/Buttons/MenuButtons/HealthRegenButton.png", 1, 2));
		addArt("ManaButton", new Image("/Images/Menus/Buttons/MenuButtons/ManaButton.png", 1, 2));
		addArt("ManaRegenButton",
				new Image("/Images/Menus/Buttons/MenuButtons/ManaRegenButton.png", 1, 2));

		addArt("PotionsButton", new Image("/Images/Menus/Buttons/MenuButtons/PotionsButton.png", 1, 2));
		addArt("InventoryButton",
				new Image("/Images/Menus/Buttons/MenuButtons/InventoryButton.png", 1, 2));

		addArt("IronDaggerButton", new Image("/Images/Menus/Buttons/Items/IronDaggerButton.png", 1, 2));
		addArt("SteelDaggerButton",
				new Image("/Images/Menus/Buttons/Items/SteelDaggerButton.png", 1, 2));
		addArt("GreatswordButton", new Image("/Images/Menus/Buttons/Items/GreatswordButton.png", 1, 2));
		addArt("SwordButton", new Image("/Images/Menus/Buttons/Items/SwordButton.png", 1, 2));
		addArt("BattleaxeButton", new Image("/Images/Menus/Buttons/Items/BattleaxeButton.png", 1, 2));
		addArt("MaceButton", new Image("/Images/Menus/Buttons/Items/MaceButton.png", 1, 2));
		addArt("LongbowButton", new Image("/Images/Menus/Buttons/Items/LongbowButton.png", 1, 2));
		addArt("ShortbowButton", new Image("/Images/Menus/Buttons/Items/ShortbowButton.png", 1, 2));
		addArt("CompoundCrossbowButton",
				new Image("/Images/Menus/Buttons/Items/CompoundCrossbowButton.png", 1, 2));
		addArt("RecurveCrossbowButton",
				new Image("/Images/Menus/Buttons/Items/RecurveCrossbowButton.png", 1, 2));
		addArt("IceStaffButton", new Image("/Images/Menus/Buttons/Items/IceStaffButton.png", 1, 2));
		addArt("FireStaffButton", new Image("/Images/Menus/Buttons/Items/FireStaffButton.png", 1, 2));
		addArt("EarthStaffButton", new Image("/Images/Menus/Buttons/Items/EarthStaffButton.png", 1, 2));

		addArt("LeatherBootsButton",
				new Image("/Images/Menus/Buttons/Items/LeatherBootsButton.png", 1, 2));
		addArt("LeatherLegsButton",
				new Image("/Images/Menus/Buttons/Items/LeatherLegsButton.png", 1, 2));
		addArt("LeatherChestButton",
				new Image("/Images/Menus/Buttons/Items/LeatherChestButton.png", 1, 2));
		addArt("LeatherHelmetButton",
				new Image("/Images/Menus/Buttons/Items/LeatherHelmetButton.png", 1, 2));
		addArt("IronBootsButton", new Image("/Images/Menus/Buttons/Items/IronBootsButton.png", 1, 2));
		addArt("IronLegsButton", new Image("/Images/Menus/Buttons/Items/IronLegsButton.png", 1, 2));
		addArt("IronChestButton", new Image("/Images/Menus/Buttons/Items/IronChestButton.png", 1, 2));
		addArt("IronHelmetButton", new Image("/Images/Menus/Buttons/Items/IronHelmetButton.png", 1, 2));

		addArt("InvButton", new Image("/Images/Menus/Buttons/Tabs/InventoryButton.png", 1, 2));
		addArt("SkillButton", new Image("/Images/Menus/Buttons/Tabs/SkillButton.png", 1, 2));
		addArt("UpgradesButton", new Image("/Images/Menus/Buttons/Tabs/UpgradesButton.png", 1, 2));
		addArt("MapButton", new Image("/Images/Menus/Buttons/Tabs/MapButton.png", 1, 2));
		addArt("SaveButton", new Image("/Images/Menus/Buttons/Tabs/SaveButton.png", 1, 2));

		addArt("LevelIcon", new Image("/Images/Menus/Icons/LevelIcon.png", 1, 1));
		addArt("DamageIcon", new Image("/Images/Menus/Icons/DamageIcon.png", 1, 1));
		addArt("ArmourIcon", new Image("/Images/Menus/Icons/ArmourIcon.png", 1, 1));
		addArt("RangeIcon", new Image("/Images/Menus/Icons/RangeIcon.png", 1, 1));
		addArt("FireRateIcon", new Image("/Images/Menus/Icons/FireRateIcon.png", 1, 1));
		addArt("ManaCostIcon", new Image("/Images/Menus/Icons/ManaCostIcon.png", 1, 1));
		addArt("CoinIcon", new Image("/Images/Menus/Icons/CoinIcon.png", 1, 1));
		addArt("WaveIcon", new Image("/Images/Menus/Icons/WaveIcon.png", 1, 1));
		addArt("LivesIcon", new Image("/Images/Menus/Icons/LivesIcon.png", 1, 1));

		// Load tile maps
		addArt("Tiles", new Image("/Images/Tiles/Tiles.png", 6, 8));

		// Load character maps
		addArt("Warrior", new Image("/Images/Characters/Players/Warrior.png", 6, 8));

		addArt("Enemy", new Image("/Images/Characters/Enemies/Enemy.png", 6, 8));
		addArt("SmallEnemy", new Image("/Images/Characters/Enemies/SmallEnemy.png", 6, 8));
		addArt("FlyingEnemy", new Image("/Images/Characters/Enemies/FlyingEnemy.png", 6, 8));
		addArt("BossEnemy", new Image("/Images/Characters/Enemies/BossEnemy.png", 6, 8));

		// Load particle maps
		addArt("Coin", new Image("/Images/Drops/Coin.png", 6, 1));

		// Load Magic maps
		addArt("EarthMagic", new Image("/Images/Particles/EarthMagic.png", 1, 8));
		addArt("FireMagic", new Image("/Images/Particles/FireMagic.png", 1, 8));
		addArt("IceMagic", new Image("/Images/Particles/IceMagic.png", 1, 8));

		addArt("Arrow", new Image("/Images/Particles/Arrow.png", 1, 8));
		addArt("SwordProjectile", new Image("/Images/Particles/Sword.png", 1, 8));

		// load status effect maps
		addArt("Fire", new Image("/Images/Effects/Fire.png", 6, 1));
		addArt("Frost", new Image("/Images/Effects/Frost.png", 6, 1));
		addArt("Poison", new Image("/Images/Effects/Poison.png", 6, 1));

		// Load Armour maps
		addArt("LeatherBoots", new Image("/Images/Drops/Armour/LeatherBoots.png", 8, 1));
		addArt("LeatherLegs", new Image("/Images/Drops/Armour/LeatherLegs.png", 4, 1));
		addArt("LeatherChest", new Image("/Images/Drops/Armour/LeatherChest.png", 4, 1));
		addArt("LeatherHelmet", new Image("/Images/Drops/Armour/LeatherHelmet.png", 7, 1));
		addArt("IronBoots", new Image("/Images/Drops/Armour/IronBoots.png", 8, 1));
		addArt("IronLegs", new Image("/Images/Drops/Armour/IronLegs.png", 4, 1));
		addArt("IronChest", new Image("/Images/Drops/Armour/IronChest.png", 4, 1));
		addArt("IronHelmet", new Image("/Images/Drops/Armour/IronHelmet.png", 7, 1));

		// Load Item maps
		addArt("Health", new Image("/Images/Drops/Potions/HealthPotion.png", 3, 1));
		addArt("Mana", new Image("/Images/Drops/Potions/ManaPotion.png", 3, 1));
		addArt("Speed", new Image("/Images/Drops/Potions/SpeedPotion.png", 3, 1));
		addArt("Knockback", new Image("/Images/Drops/Potions/KnockbackPotion.png", 3, 1));

		// Load Weapon maps
		addArt("IronDagger", new Image("/Images/Drops/Weapons/IronDagger.png", 8, 1));
		addArt("SteelDagger", new Image("/Images/Drops/Weapons/SteelDagger.png", 8, 1));
		addArt("Greatsword", new Image("/Images/Drops/Weapons/Greatsword.png", 8, 1));
		addArt("Sword", new Image("/Images/Drops/Weapons/Sword.png", 8, 1));
		addArt("Battleaxe", new Image("/Images/Drops/Weapons/Battleaxe.png", 8, 1));
		addArt("Mace", new Image("/Images/Drops/Weapons/Mace.png", 8, 1));
		addArt("Longbow", new Image("/Images/Drops/Weapons/Longbow.png", 8, 1));
		addArt("Shortbow", new Image("/Images/Drops/Weapons/Shortbow.png", 8, 1));
		addArt("RecurveCrossbow", new Image("/Images/Drops/Weapons/RecurveCrossbow.png", 8, 1));
		addArt("CompoundCrossbow", new Image("/Images/Drops/Weapons/CompoundCrossbow.png", 8, 1));
		addArt("FireStaff", new Image("/Images/Drops/Weapons/FireStaff.png", 8, 1));
		addArt("IceStaff", new Image("/Images/Drops/Weapons/IceStaff.png", 8, 1));
		addArt("EarthStaff", new Image("/Images/Drops/Weapons/EarthStaff.png", 8, 1));

		// Load HUD textures
		addArt("BarInfoTop", new Image("/Images/HUD/BarInfoTop.png", 1, 1));
		addArt("Bars", new Image("/Images/HUD/Bars.png", 4, 1));
		addArt("Numbers", new Image("/Images/HUD/Numbers.png", 10, 1));

		addArt("BarInfoBottom", new Image("/Images/HUD/BarInfoBottom.png", 1, 1));

		addArt("MapIconN", new Image("/Images/Menus/Map/MapIconN.png", 1, 1));
		addArt("MapIconW", new Image("/Images/Menus/Map/MapIconW.png", 1, 1));
		addArt("MapIconS", new Image("/Images/Menus/Map/MapIconS.png", 1, 1));
		addArt("MapIconE", new Image("/Images/Menus/Map/MapIconE.png", 1, 1));
		addArt("MapIconNW", new Image("/Images/Menus/Map/MapIconNW.png", 1, 1));
		addArt("MapIconWS", new Image("/Images/Menus/Map/MapIconWS.png", 1, 1));
		addArt("MapIconSE", new Image("/Images/Menus/Map/MapIconSE.png", 1, 1));
		addArt("MapIconEN", new Image("/Images/Menus/Map/MapIconEN.png", 1, 1));
		addArt("MapIconNWS", new Image("/Images/Menus/Map/MapIconNWS.png", 1, 1));
		addArt("MapIconWSE", new Image("/Images/Menus/Map/MapIconWSE.png", 1, 1));
		addArt("MapIconSEN", new Image("/Images/Menus/Map/MapIconSEN.png", 1, 1));
		addArt("MapIconENW", new Image("/Images/Menus/Map/MapIconENW.png", 1, 1));
		addArt("MapIconNWSE", new Image("/Images/Menus/Map/MapIconNWSE.png", 1, 1));
		addArt("MapIconNS", new Image("/Images/Menus/Map/MapIconNS.png", 1, 1));
		addArt("MapIconWE", new Image("/Images/Menus/Map/MapIconWE.png", 1, 1));
	}

}
