# Vulcan Ban Logger  

## 📌 About  
Vulcan Ban Logger is a lightweight plugin that logs all punishments from Vulcan into a structured JSON file with timestamps. This is useful for external integrations, web dashboards, or manual reviews.  

## ✨ Features  
- ✅ Logs all punishments issued by Vulcan  
- ✅ Saves data in a structured JSON format  
- ✅ Includes player name, punishment reason, type, and timestamp  
- ✅ Lightweight and efficient  

## 📂 Installation  
1. **Make sure you have Vulcan installed** on your server.  
2. **Enable the Vulcan API:**  
   - Open `plugins/Vulcan/config.yml`  
   - Set `enable-api: true` (by default, this is `false`)  
   - Restart your server for changes to take effect  
3. **Download and install Vulcan Ban Logger**  
   - Place the `VulcanBanLogger.jar` in the `plugins` folder  
   - Restart your server  

## 📄 Usage  
- Once enabled, all Vulcan punishments will be logged automatically.  
- The data is stored in `plugins/VulcanBanLogger/punishments.json`.  

## 📝 Example Log Format  
```json
[
    {
        "timestamp": "2025-03-21 14:05:30",
        "player": "Jayesh",
        "reason": "airplace",
        "punishment_type": "ban"
    }
]
