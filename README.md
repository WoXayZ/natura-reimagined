# Natura Reimagined

A remake of Natura for Minecraft **1.21.1** / **NeoForge 21.1.265**. Version **1.0**.

It is a new mod. The id is `naturareimagined`. It does not load a Natura world.

Thirteen woods, a biome for each of them, Nether plants and machines, and the old creatures. Names are in English and French.

---

## Woods

Nine Overworld woods: maple, silverbell, amaranth, tiger, willow, eucalyptus, hopseed, sakura, redwood.

Four Nether woods: ghostwood, bloodwood, fusewood, darkwood.

Each one has the vanilla wood set: log, wood, stripped log, stripped wood, planks, stairs, slab, fence, fence gate, door, trapdoor, button, pressure plate, sign, hanging sign, and a bookshelf. Bookshelves count for enchanting. Connected bookshelf textures need [Connected Textures Mod](https://www.curseforge.com/minecraft/mc-mods/ctm), which is optional.

Overworld woods have a boat and a chest boat. Nether woods do not, and they do not burn.

A few woods do more than sit there:

- **Willow** hangs leaves down, including into water.
- **Eucalyptus** is a tall trunk with a flat crown.
- **Hopseed** is a wide, flat crown. Some are bigger.
- **Redwood** is the tall one. The forest always grows them. Extra wild redwoods follow `generateRedwood` in the config, off by default. Saplings still grow. Height for those trees is configurable, default 24 to 40.
- **Sakura** is its own tree. It is not the vanilla cherry.
- **Bloodwood** hangs from the Nether ceiling. The sapling grows the same way.
- **Fusewood** logs explode when a player breaks them. Peaceful never explodes. By default the blast does not break blocks.
- **Darkwood** sometimes grows flowering leaves or fruit leaves.

---

## Biomes

Biomes are added with TerraBlender. Natura takes some vanilla biomes instead of stacking another forest on top of the old one.

| Vanilla biome | Replaced by |
| --- | --- |
| Forest | Hopseed Woodland |
| Birch Forest | Maple Woods |
| Cherry Grove | Sakura Grove |
| Taiga | Silverbell Forest |
| Swamp | Willow Marsh |
| Savanna | Eucalyptus Grove |
| Old Growth Pine Taiga | Redwood Forest |
| Jungle | Tigerwood Jungle |
| Dark Forest | Amaranth Canopy |

The Overworld region weight is 4.

The Nether keeps Nether Wastes and adds Ghostwood Forest, Bloodwood Wilds, Darkwood Thicket, Fusewood Wastes, and Fungal Depths. The Nether region weight is 3. Each Nether wood biome has its own nylium. Fungal Depths grows the big glowshrooms, with patches of tainted soil.

Maple Woods uses autumnal grass. Silverbell Forest and Eucalyptus Grove use bluegrass. Amaranth Canopy uses topiary grass. Redwood Forest mixes grass and podzol.

---

## Plants and food

Berry bushes stack. Overworld: raspberry and blackberry in forests, blueberry on mountains, maloberry in swamps. Nether, in low light: blightberry, duskberry, skyberry, stingberry. Those four apply a short effect. A bowl of berry medley uses one of each.

Barley and cotton grow on farmland. Breaking grass can drop their seeds. Barley mills to flour and smelts into bread. Cotton crafts into string. Wheat can do the same flour recipe. That recipe can be turned off.

Seed bags plant a 3×3 of wheat, carrots, potatoes, beetroot, nether wart, barley, or cotton. The bone meal bag does the same for bone meal. The empty bag comes back.

Bluebells grow in forests. Saguaro grows in deserts and in the Nether. The fruit makes cactus juice. Walking into the cactus hurts.

Glowshrooms come in green, blue, and purple. Bone meal grows the huge ones. A bowl of stew is food, and using that stew on a mooshroom turns it into a glowshroom cow of the same color. A bowl on the cow gives more stew. Shears turn it back into a cow and drop glowshrooms.

Pyrefly bushes sit on nylium and give off small lights.

---

## Nether blocks

The netherrack furnace cooks from a fire or soul fire directly under it, with no fuel. Fuel still works if nothing is burning below. That behavior can be turned off, and it becomes a furnace made of netherrack.

Blaze rails are the four rail types, with a higher minecart speed (default 0.8, vanilla is 0.4). The blaze hopper pulls from a wider area above it. Flint and blaze lights a small patch of fire and wears out.

Heat sand falls and burns bare feet. Tainted soil can be hoed into farmland. Crops on heated tainted farmland grow faster. Thorn vines hurt like a cactus.

Clouds slow a fall. White, dark, and ash are safe. Sulfur clouds explode if you light them or shoot them with a flaming arrow. Heat glass hurts anything inside it. Soul glass applies slowness.

---

## Creatures

**Imp.** Passive. It runs from you unless you are holding glowshroom stew or a berry medley, which is also what it breeds with. It drops imp meat and imp leather. The leather makes a leather-grade armor set.

**Hellhound.** A wolf. Wild ones hunt imps. Feed it imp meat to tame it. Its bites set the target on fire. Tamed pups stay tame.

**Cinderlin.** A hostile piglin. It spawns with a quartz sword, a quartz axe, or a flame crossbow. Give it a gold ingot and it throws a barter drop. JEI, EMI, and REI show that trade if you have them.

**Ashen skeleton.** A skeleton with a flame crossbow. They also show up in Nether fortresses.

**Nitro creeper.** A creeper with a short fuse. An iron golem kills one in a hit.

**Heatscar spider.** Off by default. It sets fire to whatever it bites, other than players, and leaves babies behind when it dies.

**Bats.** Ghost bats hang around the cooler Nether biomes. Ember bats set you on fire if they brush you.

**The rest.** Sakura foxes in the grove, amaranth bees in the canopy, willow frogs in the marsh. A willow frog that eats a tiny slime can leave a froglight. Glowshroom cows are made from mooshrooms, as above.

---

## Tools

Hatchets go from wood to netherite, plus quartz. They mine wood faster than the axe of the same tier, deal less damage, and swing faster. Tool enchantments only.

The quartz set sits between stone and iron. The hoe is the quicker one.

---

## Configuration

`config/naturareimagined-common.toml`.

The flags worth knowing:

- `generateRedwood` is off. Redwood Forest still has its trees. This flag is the extra wild ones.
- `spawnHeatscar` is off.
- Fusewood blast power, and whether that blast breaks blocks.
- Berry bushes, bluebells, clouds, saguaro, glowshrooms, thorn vines, tainted soil, heat sand, and how rare they are.
- Grass dropping barley and cotton seeds.
- The wheat flour recipe.
- Netherrack furnace cooking from the fire under it.
- Blaze rail speed, flint and blaze durability.
- Imp and nitro creeper spawns.
- Extra chest loot in the Overworld and the Nether.

---

## Requirements

| Dependency | Required | Version |
| --- | --- | --- |
| Minecraft | yes | 1.21.1 |
| NeoForge | yes | 21.1.0+ |
| Java | yes | 21 |
| [TerraBlender](https://www.curseforge.com/minecraft/mc-mods/terrablender) | yes | 4.1.0.0+ |
| [Connected Textures Mod](https://www.curseforge.com/minecraft/mc-mods/ctm) | no | 1.21+ (bookshelf connections) |
| JEI, EMI, or REI | no | cinderlin barter |

Built against NeoForge 21.1.265.

---

## Building

```bash
./gradlew jar
```

On Windows: `gradlew.bat jar`.

The jar lands in `build/libs/naturareimagined-1.21.1-1.0.jar`.

```bash
./gradlew runClient
./gradlew runData
```

`runData` regenerates `src/generated`. Those files are committed.

---

## Credits

Original Natura by mDiyo and the Slime Knights.

This remake is by WoXayZ. It shares no code with the old mod or with the later ports. Not an official continuation.

## License

MIT.
