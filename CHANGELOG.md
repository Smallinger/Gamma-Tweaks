# Changelog

All notable changes to this project will be documented in this file.

The format is based on [Keep a Changelog](https://keepachangelog.com/en/1.0.0/),
and this project adheres to [Semantic Versioning](https://semver.org/spec/v2.0.0.html).

## [1.0.0] - 2025-11-14

### Initial Release

This is the first official release of Gamma Tweaks for NeoForge 1.21.10!

#### Added
- **Extended Gamma Range**: Adjust brightness from -750% to 1500% (far beyond vanilla limits)
- **Custom Night Vision**: Enable night vision without potions (0-100% strength)
- **Dynamic Gamma**: Automatically adjusts gamma based on surrounding light levels
- **Smooth Transitions**: Optional smooth transitions between gamma levels for a more natural experience
- **Status Effects**: Visual status effect indicators for gamma and night vision in the HUD
- **Customizable Keybindings**: 
  - `G` - Toggle Gamma
  - `↑/↓` - Increase/Decrease Gamma
  - `H` - Toggle Night Vision
  - `→/←` - Increase/Decrease Night Vision
- **Dimension Preferences**: Set different gamma/night vision settings per dimension (Overworld, Nether, End)
- **HUD Messages**: Visual feedback with customizable colors for all actions
- **Commands**: Full command support for precise control
  - `/gamma` - Gamma control commands
  - `/nightvision` - Night vision control commands
- **Shader Detection**: Automatic detection of Iris/Oculus shaders with warnings when gamma is activated
- **Configuration System**: Comprehensive NeoForge TOML-based config system accessible via Mods Menu

#### Technical Details
- Inspired from Fabric's [Gamma Utils](https://github.com/sjouwer/gamma-utils)
- Migrated from AutoConfig (JSON) to NeoForge's ModConfigSpec (TOML)
- Uses NeoForge's event system (`RegisterKeyMappingsEvent`, `LevelTickEvent.Pre`, etc.)
- Mixin-based implementation for gamma/brightness overrides
- Client-side only mod - works on any server without server-side installation

#### Compatibility
- **Minecraft**: 1.21.10
- **NeoForge**: 21.10.47-beta or higher
- **Java**: 21
- **Compatible with**: Sodium, Lithium, Iris Shaders (with automatic detection)

#### Credits
- Original Fabric mod: [Gamma Utils](https://github.com/sjouwer/gamma-utils) by Sjouwer
- Status effect icons: Licensed under GNU LGPL v3.0 from Gamma Utils
- Port and NeoForge adaptation: Smallinger

---

[1.0.0]: https://github.com/Smallinger/Gamma-Tweaks/releases/tag/v1.0.0
