const fs = require('fs');

const variants = ['acacia', 'birch', 'dark_oak', 'jungle', 'oak', 'spruce', 'crimson', 'warped']
const tiers = ['default', 'tier1', 'tier2', 'tier3']

for (let variant of variants) {
    for (let tier of tiers) {
        console.log("creating loot table for block drop " + variant);

        const name = `${variant}_chest_${tier}`

        let drop = {
            "type": "minecraft:block",
            "pools": [
                {
                    "rolls": 1,
                    "entries": [
                        {
                            "type": "minecraft:item",
                            "functions": [
                                {
                                    "function": "minecraft:copy_name",
                                    "source": "block_entity"
                                }
                            ],
                            "name": `packed:${name}`
                        }
                    ],
                    "conditions": [
                        {
                            "condition": "minecraft:survives_explosion"
                        }
                    ]
                }
            ]
        }

        const data = JSON.stringify(drop, null, 4);
        fs.writeFile(`${name}.json`, data, (err) => {
            if (err) {
                throw err;
            }
        });
    }
}