package display;

public class Art implements ArtLoader {

	@Override
	public void loadArt() {

		// load menu screen art
		ImageProcessor.addArt("MainMenuScreen", new Image("/Images/Menus/Backgrounds/MainMenuScreen.png", 1, 1));
		ImageProcessor.addArt("InventoryScreen", new Image("/Images/Menus/Backgrounds/InventoryScreen.png", 1, 1));
		ImageProcessor.addArt("SkillScreen", new Image("/Images/Menus/Backgrounds/SkillScreen.png", 1, 1));
		ImageProcessor.addArt("UpgradesScreen", new Image("/Images/Menus/Backgrounds/UpgradesScreen.png", 1, 1));
		ImageProcessor.addArt("MapScreen", new Image("/Images/Menus/Backgrounds/MapScreen.png", 1, 1));
		ImageProcessor.addArt("SaveScreen", new Image("/Images/Menus/Backgrounds/SaveScreen.png", 1, 1));
		ImageProcessor.addArt("GameOverScreen", new Image("/Images/Menus/Backgrounds/GameOverScreen.png", 1, 1));

		// load button art
		ImageProcessor.addArt("NewGameButton", new Image("/Images/Menus/Buttons/MenuButtons/NewGameButton.png", 1, 2));

		ImageProcessor.addArt("LoadGameButton",
				new Image("/Images/Menus/Buttons/MenuButtons/LoadGameButton.png", 1, 2));
		ImageProcessor.addArt("OptionsButton", new Image("/Images/Menus/Buttons/MenuButtons/OptionsButton.png", 1, 2));
		ImageProcessor.addArt("ExitButton", new Image("/Images/Menus/Buttons/MenuButtons/ExitButton.png", 1, 2));

		ImageProcessor.addArt("BackButton", new Image("/Images/Menus/Buttons/MenuButtons/BackButton.png", 1, 2));

		ImageProcessor.addArt("ControlsButton",
				new Image("/Images/Menus/Buttons/MenuButtons/ControlsButton.png", 1, 2));
		ImageProcessor.addArt("SoundButton", new Image("/Images/Menus/Buttons/MenuButtons/SoundButton.png", 1, 2));

		ImageProcessor.addArt("MuteButton", new Image("/Images/Menus/Buttons/MenuButtons/MuteButton.png", 1, 2));

		ImageProcessor.addArt("SaveGameButton",
				new Image("/Images/Menus/Buttons/MenuButtons/SaveGameButton.png", 1, 2));

		ImageProcessor.addArt("Save1Button", new Image("/Images/Menus/Buttons/MenuButtons/Save1Button.png", 1, 2));
		ImageProcessor.addArt("Save2Button", new Image("/Images/Menus/Buttons/MenuButtons/Save2Button.png", 1, 2));
		ImageProcessor.addArt("Save3Button", new Image("/Images/Menus/Buttons/MenuButtons/Save3Button.png", 1, 2));

		ImageProcessor.addArt("HealthButton", new Image("/Images/Menus/Buttons/MenuButtons/HealthButton.png", 1, 2));
		ImageProcessor.addArt("HealthRegenButton",
				new Image("/Images/Menus/Buttons/MenuButtons/HealthRegenButton.png", 1, 2));
		ImageProcessor.addArt("ManaButton", new Image("/Images/Menus/Buttons/MenuButtons/ManaButton.png", 1, 2));
		ImageProcessor.addArt("ManaRegenButton",
				new Image("/Images/Menus/Buttons/MenuButtons/ManaRegenButton.png", 1, 2));

		ImageProcessor.addArt("PotionsButton", new Image("/Images/Menus/Buttons/MenuButtons/PotionsButton.png", 1, 2));
		ImageProcessor.addArt("InventoryButton",
				new Image("/Images/Menus/Buttons/MenuButtons/InventoryButton.png", 1, 2));

		ImageProcessor.addArt("IronDaggerButton", new Image("/Images/Menus/Buttons/Items/IronDaggerButton.png", 1, 2));
		ImageProcessor.addArt("SteelDaggerButton",
				new Image("/Images/Menus/Buttons/Items/SteelDaggerButton.png", 1, 2));
		ImageProcessor.addArt("GreatswordButton", new Image("/Images/Menus/Buttons/Items/GreatswordButton.png", 1, 2));
		ImageProcessor.addArt("SwordButton", new Image("/Images/Menus/Buttons/Items/SwordButton.png", 1, 2));
		ImageProcessor.addArt("BattleaxeButton", new Image("/Images/Menus/Buttons/Items/BattleaxeButton.png", 1, 2));
		ImageProcessor.addArt("MaceButton", new Image("/Images/Menus/Buttons/Items/MaceButton.png", 1, 2));
		ImageProcessor.addArt("LongbowButton", new Image("/Images/Menus/Buttons/Items/LongbowButton.png", 1, 2));
		ImageProcessor.addArt("ShortbowButton", new Image("/Images/Menus/Buttons/Items/ShortbowButton.png", 1, 2));
		ImageProcessor.addArt("CompoundCrossbowButton",
				new Image("/Images/Menus/Buttons/Items/CompoundCrossbowButton.png", 1, 2));
		ImageProcessor.addArt("RecurveCrossbowButton",
				new Image("/Images/Menus/Buttons/Items/RecurveCrossbowButton.png", 1, 2));
		ImageProcessor.addArt("IceStaffButton", new Image("/Images/Menus/Buttons/Items/IceStaffButton.png", 1, 2));
		ImageProcessor.addArt("FireStaffButton", new Image("/Images/Menus/Buttons/Items/FireStaffButton.png", 1, 2));
		ImageProcessor.addArt("EarthStaffButton", new Image("/Images/Menus/Buttons/Items/EarthStaffButton.png", 1, 2));

		ImageProcessor.addArt("LeatherBootsButton",
				new Image("/Images/Menus/Buttons/Items/LeatherBootsButton.png", 1, 2));
		ImageProcessor.addArt("LeatherLegsButton",
				new Image("/Images/Menus/Buttons/Items/LeatherLegsButton.png", 1, 2));
		ImageProcessor.addArt("LeatherChestButton",
				new Image("/Images/Menus/Buttons/Items/LeatherChestButton.png", 1, 2));
		ImageProcessor.addArt("LeatherHelmetButton",
				new Image("/Images/Menus/Buttons/Items/LeatherHelmetButton.png", 1, 2));
		ImageProcessor.addArt("IronBootsButton", new Image("/Images/Menus/Buttons/Items/IronBootsButton.png", 1, 2));
		ImageProcessor.addArt("IronLegsButton", new Image("/Images/Menus/Buttons/Items/IronLegsButton.png", 1, 2));
		ImageProcessor.addArt("IronChestButton", new Image("/Images/Menus/Buttons/Items/IronChestButton.png", 1, 2));
		ImageProcessor.addArt("IronHelmetButton", new Image("/Images/Menus/Buttons/Items/IronHelmetButton.png", 1, 2));

		ImageProcessor.addArt("InvButton", new Image("/Images/Menus/Buttons/Tabs/InventoryButton.png", 1, 2));
		ImageProcessor.addArt("SkillButton", new Image("/Images/Menus/Buttons/Tabs/SkillButton.png", 1, 2));
		ImageProcessor.addArt("UpgradesButton", new Image("/Images/Menus/Buttons/Tabs/UpgradesButton.png", 1, 2));
		ImageProcessor.addArt("MapButton", new Image("/Images/Menus/Buttons/Tabs/MapButton.png", 1, 2));
		ImageProcessor.addArt("SaveButton", new Image("/Images/Menus/Buttons/Tabs/SaveButton.png", 1, 2));

		ImageProcessor.addArt("LevelIcon", new Image("/Images/Menus/Icons/LevelIcon.png", 1, 1));
		ImageProcessor.addArt("DamageIcon", new Image("/Images/Menus/Icons/DamageIcon.png", 1, 1));
		ImageProcessor.addArt("ArmourIcon", new Image("/Images/Menus/Icons/ArmourIcon.png", 1, 1));
		ImageProcessor.addArt("RangeIcon", new Image("/Images/Menus/Icons/RangeIcon.png", 1, 1));
		ImageProcessor.addArt("FireRateIcon", new Image("/Images/Menus/Icons/FireRateIcon.png", 1, 1));
		ImageProcessor.addArt("ManaCostIcon", new Image("/Images/Menus/Icons/ManaCostIcon.png", 1, 1));
		ImageProcessor.addArt("CoinIcon", new Image("/Images/Menus/Icons/CoinIcon.png", 1, 1));
		ImageProcessor.addArt("WaveIcon", new Image("/Images/Menus/Icons/WaveIcon.png", 1, 1));
		ImageProcessor.addArt("LivesIcon", new Image("/Images/Menus/Icons/LivesIcon.png", 1, 1));

		// Load tile maps
		ImageProcessor.addArt("Tiles", new Image("/Images/Tiles/Tiles.png", 6, 8));

		// Load character maps
		ImageProcessor.addArt("Warrior", new Image("/Images/Characters/Players/Warrior.png", 6, 8));

		ImageProcessor.addArt("Enemy", new Image("/Images/Characters/Enemies/Enemy.png", 6, 8));
		ImageProcessor.addArt("SmallEnemy", new Image("/Images/Characters/Enemies/SmallEnemy.png", 6, 8));
		ImageProcessor.addArt("FlyingEnemy", new Image("/Images/Characters/Enemies/FlyingEnemy.png", 6, 8));
		ImageProcessor.addArt("BossEnemy", new Image("/Images/Characters/Enemies/BossEnemy.png", 6, 8));

		// Load particle maps
		ImageProcessor.addArt("Coin", new Image("/Images/Drops/Coin.png", 6, 1));

		// Load Magic maps
		ImageProcessor.addArt("EarthMagic", new Image("/Images/Particles/EarthMagic.png", 1, 8));
		ImageProcessor.addArt("FireMagic", new Image("/Images/Particles/FireMagic.png", 1, 8));
		ImageProcessor.addArt("IceMagic", new Image("/Images/Particles/IceMagic.png", 1, 8));

		ImageProcessor.addArt("Arrow", new Image("/Images/Particles/Arrow.png", 1, 8));
		ImageProcessor.addArt("SwordProjectile", new Image("/Images/Particles/Sword.png", 1, 8));

		// load status effect maps
		ImageProcessor.addArt("Fire", new Image("/Images/Effects/Fire.png", 6, 1));
		ImageProcessor.addArt("Frost", new Image("/Images/Effects/Frost.png", 6, 1));
		ImageProcessor.addArt("Poison", new Image("/Images/Effects/Poison.png", 6, 1));

		// Load Armour maps
		ImageProcessor.addArt("LeatherBoots", new Image("/Images/Drops/Armour/LeatherBoots.png", 8, 1));
		ImageProcessor.addArt("LeatherLegs", new Image("/Images/Drops/Armour/LeatherLegs.png", 4, 1));
		ImageProcessor.addArt("LeatherChest", new Image("/Images/Drops/Armour/LeatherChest.png", 4, 1));
		ImageProcessor.addArt("LeatherHelmet", new Image("/Images/Drops/Armour/LeatherHelmet.png", 7, 1));
		ImageProcessor.addArt("IronBoots", new Image("/Images/Drops/Armour/IronBoots.png", 8, 1));
		ImageProcessor.addArt("IronLegs", new Image("/Images/Drops/Armour/IronLegs.png", 4, 1));
		ImageProcessor.addArt("IronChest", new Image("/Images/Drops/Armour/IronChest.png", 4, 1));
		ImageProcessor.addArt("IronHelmet", new Image("/Images/Drops/Armour/IronHelmet.png", 7, 1));

		// Load Item maps
		ImageProcessor.addArt("Health", new Image("/Images/Drops/Potions/HealthPotion.png", 3, 1));
		ImageProcessor.addArt("Mana", new Image("/Images/Drops/Potions/ManaPotion.png", 3, 1));
		ImageProcessor.addArt("Speed", new Image("/Images/Drops/Potions/SpeedPotion.png", 3, 1));
		ImageProcessor.addArt("Knockback", new Image("/Images/Drops/Potions/KnockbackPotion.png", 3, 1));

		// Load Weapon maps
		ImageProcessor.addArt("IronDagger", new Image("/Images/Drops/Weapons/IronDagger.png", 8, 1));
		ImageProcessor.addArt("SteelDagger", new Image("/Images/Drops/Weapons/SteelDagger.png", 8, 1));
		ImageProcessor.addArt("Greatsword", new Image("/Images/Drops/Weapons/Greatsword.png", 8, 1));
		ImageProcessor.addArt("Sword", new Image("/Images/Drops/Weapons/Sword.png", 8, 1));
		ImageProcessor.addArt("Battleaxe", new Image("/Images/Drops/Weapons/Battleaxe.png", 8, 1));
		ImageProcessor.addArt("Mace", new Image("/Images/Drops/Weapons/Mace.png", 8, 1));
		ImageProcessor.addArt("Longbow", new Image("/Images/Drops/Weapons/Longbow.png", 8, 1));
		ImageProcessor.addArt("Shortbow", new Image("/Images/Drops/Weapons/Shortbow.png", 8, 1));
		ImageProcessor.addArt("RecurveCrossbow", new Image("/Images/Drops/Weapons/RecurveCrossbow.png", 8, 1));
		ImageProcessor.addArt("CompoundCrossbow", new Image("/Images/Drops/Weapons/CompoundCrossbow.png", 8, 1));
		ImageProcessor.addArt("FireStaff", new Image("/Images/Drops/Weapons/FireStaff.png", 8, 1));
		ImageProcessor.addArt("IceStaff", new Image("/Images/Drops/Weapons/IceStaff.png", 8, 1));
		ImageProcessor.addArt("EarthStaff", new Image("/Images/Drops/Weapons/EarthStaff.png", 8, 1));

		// Load HUD textures
		ImageProcessor.addArt("BarInfoTop", new Image("/Images/HUD/BarInfoTop.png", 1, 1));
		ImageProcessor.addArt("Bars", new Image("/Images/HUD/Bars.png", 4, 1));
		ImageProcessor.addArt("Numbers", new Image("/Images/HUD/Numbers.png", 10, 1));

		ImageProcessor.addArt("BarInfoBottom", new Image("/Images/HUD/BarInfoBottom.png", 1, 1));

		ImageProcessor.addArt("MazeIconN", new Image("/Images/Menu/Maze/MazeIconN.png", 1, 1));
		ImageProcessor.addArt("MazeIconW", new Image("/Images/Menu/Maze/MazeIconW.png", 1, 1));
		ImageProcessor.addArt("MazeIconS", new Image("/Images/Menu/Maze/MazeIconS.png", 1, 1));
		ImageProcessor.addArt("MazeIconE", new Image("/Images/Menu/Maze/MazeIconE.png", 1, 1));
		ImageProcessor.addArt("MazeIconNW", new Image("/Images/Menu/Maze/MazeIconNW.png", 1, 1));
		ImageProcessor.addArt("MazeIconWS", new Image("/Images/Menu/Maze/MazeIconWS.png", 1, 1));
		ImageProcessor.addArt("MazeIconSE", new Image("/Images/Menu/Maze/MazeIconSE.png", 1, 1));
		ImageProcessor.addArt("MazeIconEN", new Image("/Images/Menu/Maze/MazeIconEN.png", 1, 1));
		ImageProcessor.addArt("MazeIconNWS", new Image("/Images/Menu/Maze/MazeIconNWS.png", 1, 1));
		ImageProcessor.addArt("MazeIconWSE", new Image("/Images/Menu/Maze/MazeIconWSE.png", 1, 1));
		ImageProcessor.addArt("MazeIconSEN", new Image("/Images/Menu/Maze/MazeIconSEN.png", 1, 1));
		ImageProcessor.addArt("MazeIconENW", new Image("/Images/Menu/Maze/MazeIconENW.png", 1, 1));
		ImageProcessor.addArt("MazeIconNWES", new Image("/Images/Menu/Maze/MazeIconNWES.png", 1, 1));
		ImageProcessor.addArt("MazeIconNS", new Image("/Images/Menu/Maze/MazeIconNS.png", 1, 1));
		ImageProcessor.addArt("MazeIconWE", new Image("/Images/Menu/Maze/MazeIconWE.png", 1, 1));
	}

}
