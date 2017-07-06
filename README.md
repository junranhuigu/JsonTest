# JsonTest
Json analysis tools. Just for fun.

Not support for array has list or list contains array.If you use those things, please try to parse those to list contains list or array with array.
Ps: you should use List<List<String>> or String[][] those type as member of Class.

2017-7-6 add:
Can use Json.select() get the attribute info from json String

For example: 
String json = "{\"cardList\":[{\"id\":70045,\"number\":0,\"pieceNumber\":0},{\"id\":70002,\"number\":1,\"pieceNumber\":0},{\"id\":70051,\"number\":0,\"pieceNumber\":0},{\"id\":70058,\"number\":3,\"pieceNumber\":0},{\"id\":70048,\"number\":0,\"pieceNumber\":0},{\"id\":70028,\"number\":0,\"pieceNumber\":0},{\"id\":70010,\"number\":0,\"pieceNumber\":0},{\"id\":70052,\"number\":0,\"pieceNumber\":0},{\"id\":70012,\"number\":0,\"pieceNumber\":0},{\"id\":70053,\"number\":0,\"pieceNumber\":0},{\"id\":70055,\"number\":0,\"pieceNumber\":0},{\"id\":70024,\"number\":1,\"pieceNumber\":0}],\"charmList\":[],\"clothNew\":80001,\"clouthList\":[{\"id\":80001,\"isWear\":1}],\"eightList\":[{\"level\":20,\"stage\":1,\"stageexp\":0,\"type\":1,\"valueList\":[[1,53]],\"valueList2\":[[6,18]]},{\"level\":2,\"stage\":0,\"stageexp\":0,\"type\":2,\"valueList\":[[3,204]],\"valueList2\":[]},{\"level\":20,\"stage\":1,\"stageexp\":0,\"type\":3,\"valueList\":[[2,77]],\"valueList2\":[[8,14]]},{\"level\":2,\"stage\":0,\"stageexp\":0,\"type\":4,\"valueList\":[[4,11]],\"valueList2\":[]},{\"level\":17,\"stage\":1,\"stageexp\":0,\"type\":5,\"valueList\":[[5,168]],\"valueList2\":[[0,0]]},{\"level\":9,\"stage\":0,\"stageexp\":0,\"type\":6,\"valueList\":[[6,42]],\"valueList2\":[]},{\"level\":20,\"stage\":1,\"stageexp\":0,\"type\":7,\"valueList\":[[7,200]],\"valueList2\":[[7,22]]},{\"level\":1,\"stage\":0,\"stageexp\":0,\"type\":8,\"valueList\":[[8,0]],\"valueList2\":[]}],\"id\":\"2210095328662901\",\"itemList\":[{\"itemId\":50040,\"num\":10}],\"piece\":[{\"itemId\":91004,\"num\":2},{\"itemId\":92006,\"num\":8},{\"itemId\":92007,\"num\":4},{\"itemId\":91002,\"num\":5},{\"itemId\":91003,\"num\":3}],\"soulList\":[{\"breakLevel\":2,\"id\":21044,\"mosaicPosition\":0,\"skillPosition\":1,\"strengthLevel\":21,\"uuid\":\"yCJOt\"},{\"breakLevel\":2,\"id\":21035,\"mosaicPosition\":0,\"skillPosition\":6,\"strengthLevel\":21,\"uuid\":\"NDsOk\"},{\"breakLevel\":2,\"id\":21040,\"mosaicPosition\":0,\"skillPosition\":2,\"strengthLevel\":21,\"uuid\":\"3B7rH\"},{\"breakLevel\":0,\"id\":21001,\"mosaicPosition\":0,\"skillPosition\":0,\"strengthLevel\":0,\"uuid\":\"WNjjG\"},{\"breakLevel\":2,\"id\":21033,\"mosaicPosition\":0,\"skillPosition\":7,\"strengthLevel\":21,\"uuid\":\"KcMbM\"},{\"breakLevel\":0,\"id\":21012,\"mosaicPosition\":0,\"skillPosition\":0,\"strengthLevel\":0,\"uuid\":\"Hrp7l\"},{\"breakLevel\":2,\"id\":21003,\"mosaicPosition\":0,\"skillPosition\":8,\"strengthLevel\":21,\"uuid\":\"QXN5Q\"},{\"breakLevel\":0,\"id\":21038,\"mosaicPosition\":0,\"skillPosition\":0,\"strengthLevel\":0,\"uuid\":\"4PU3Q\"},{\"breakLevel\":0,\"id\":21042,\"mosaicPosition\":0,\"skillPosition\":0,\"strengthLevel\":0,\"uuid\":\"VhqW9\"},{\"breakLevel\":0,\"id\":21001,\"mosaicPosition\":0,\"skillPosition\":0,\"strengthLevel\":0,\"uuid\":\"aff2i\"},{\"breakLevel\":0,\"id\":21012,\"mosaicPosition\":0,\"skillPosition\":0,\"strengthLevel\":0,\"uuid\":\"8330z\"},{\"breakLevel\":0,\"id\":21039,\"mosaicPosition\":0,\"skillPosition\":0,\"strengthLevel\":0,\"uuid\":\"Yk7Qc\"},{\"breakLevel\":0,\"id\":21038,\"mosaicPosition\":0,\"skillPosition\":0,\"strengthLevel\":0,\"uuid\":\"T5IXN\"},{\"breakLevel\":2,\"id\":21014,\"mosaicPosition\":0,\"skillPosition\":3,\"strengthLevel\":21,\"uuid\":\"QQqYr\"},{\"breakLevel\":0,\"id\":21042,\"mosaicPosition\":0,\"skillPosition\":0,\"strengthLevel\":0,\"uuid\":\"1p8Sp\"},{\"breakLevel\":0,\"id\":21001,\"mosaicPosition\":0,\"skillPosition\":0,\"strengthLevel\":0,\"uuid\":\"Jv7JD\"},{\"breakLevel\":0,\"id\":21012,\"mosaicPosition\":0,\"skillPosition\":0,\"strengthLevel\":0,\"uuid\":\"ZqKO6\"},{\"breakLevel\":0,\"id\":21040,\"mosaicPosition\":0,\"skillPosition\":0,\"strengthLevel\":0,\"uuid\":\"ELie1\"},{\"breakLevel\":0,\"id\":21038,\"mosaicPosition\":0,\"skillPosition\":0,\"strengthLevel\":0,\"uuid\":\"1Nsk8\"},{\"breakLevel\":0,\"id\":21042,\"mosaicPosition\":0,\"skillPosition\":0,\"strengthLevel\":0,\"uuid\":\"PTtLO\"},{\"breakLevel\":0,\"id\":21001,\"mosaicPosition\":0,\"skillPosition\":0,\"strengthLevel\":0,\"uuid\":\"24CtR\"},{\"breakLevel\":0,\"id\":21012,\"mosaicPosition\":0,\"skillPosition\":0,\"strengthLevel\":0,\"uuid\":\"Dwm7z\"},{\"breakLevel\":0,\"id\":21039,\"mosaicPosition\":0,\"skillPosition\":0,\"strengthLevel\":0,\"uuid\":\"FCj6Y\"},{\"breakLevel\":0,\"id\":21038,\"mosaicPosition\":0,\"skillPosition\":0,\"strengthLevel\":0,\"uuid\":\"XHXA5\"},{\"breakLevel\":0,\"id\":21042,\"mosaicPosition\":0,\"skillPosition\":0,\"strengthLevel\":0,\"uuid\":\"gstGi\"},{\"breakLevel\":0,\"id\":21001,\"mosaicPosition\":0,\"skillPosition\":0,\"strengthLevel\":0,\"uuid\":\"OIdrC\"},{\"breakLevel\":0,\"id\":21012,\"mosaicPosition\":0,\"skillPosition\":0,\"strengthLevel\":0,\"uuid\":\"Kkfbd\"},{\"breakLevel\":0,\"id\":21038,\"mosaicPosition\":0,\"skillPosition\":0,\"strengthLevel\":0,\"uuid\":\"Fvddd\"},{\"breakLevel\":0,\"id\":21042,\"mosaicPosition\":0,\"skillPosition\":0,\"strengthLevel\":0,\"uuid\":\"NKlKX\"},{\"breakLevel\":0,\"id\":21013,\"mosaicPosition\":0,\"skillPosition\":0,\"strengthLevel\":0,\"uuid\":\"etuxT\"},{\"breakLevel\":0,\"id\":21001,\"mosaicPosition\":0,\"skillPosition\":0,\"strengthLevel\":0,\"uuid\":\"rXtkv\"},{\"breakLevel\":0,\"id\":21012,\"mosaicPosition\":0,\"skillPosition\":0,\"strengthLevel\":0,\"uuid\":\"bK0rB\"},{\"breakLevel\":0,\"id\":21013,\"mosaicPosition\":0,\"skillPosition\":0,\"strengthLevel\":0,\"uuid\":\"rhRsv\"},{\"breakLevel\":0,\"id\":21038,\"mosaicPosition\":0,\"skillPosition\":0,\"strengthLevel\":0,\"uuid\":\"1jH5h\"},{\"breakLevel\":0,\"id\":21042,\"mosaicPosition\":0,\"skillPosition\":0,\"strengthLevel\":0,\"uuid\":\"ehkBX\"},{\"breakLevel\":1,\"id\":21006,\"mosaicPosition\":0,\"skillPosition\":4,\"strengthLevel\":19,\"uuid\":\"CQDdz\"},{\"breakLevel\":0,\"id\":21038,\"mosaicPosition\":0,\"skillPosition\":0,\"strengthLevel\":0,\"uuid\":\"ZrwAn\"},{\"breakLevel\":0,\"id\":21029,\"mosaicPosition\":0,\"skillPosition\":0,\"strengthLevel\":0,\"uuid\":\"YX5HZ\"}],\"weaponList\":[{\"exp\":0,\"isOk\":-1,\"isWear\":0,\"level\":8,\"skillId\":10358,\"valueList\":[[1,210],[4,20],[5,26],[6,19]],\"weaponId\":10001},{\"exp\":0,\"isOk\":-1,\"isWear\":1,\"level\":8,\"skillId\":10359,\"valueList\":[[1,373],[4,34],[5,44],[6,33]],\"weaponId\":10002}],\"weaponNew\":10002,\"wearCharmList\":[]}";

Use Json.jsonStructure(json) you can see the json's structure.
The execute result is :
root{Object}
root{Object}.cardList{Array}
root{Object}.cardList{Array}{Object}
root{Object}.cardList{Array}{Object}.id
root{Object}.cardList{Array}{Object}.number
root{Object}.cardList{Array}{Object}.pieceNumber
root{Object}.charmList{Array}
root{Object}.clothNew
root{Object}.clouthList{Array}
root{Object}.clouthList{Array}{Object}
root{Object}.clouthList{Array}{Object}.id
root{Object}.clouthList{Array}{Object}.isWear
root{Object}.eightList{Array}
root{Object}.eightList{Array}{Object}
root{Object}.eightList{Array}{Object}.level
root{Object}.eightList{Array}{Object}.stage
root{Object}.eightList{Array}{Object}.stageexp
root{Object}.eightList{Array}{Object}.type
root{Object}.eightList{Array}{Object}.valueList2{Array}
root{Object}.eightList{Array}{Object}.valueList2{Array}{Array}
root{Object}.eightList{Array}{Object}.valueList{Array}
root{Object}.eightList{Array}{Object}.valueList{Array}{Array}
root{Object}.id
root{Object}.itemList{Array}
root{Object}.itemList{Array}{Object}
root{Object}.itemList{Array}{Object}.itemId
root{Object}.itemList{Array}{Object}.num
root{Object}.piece{Array}
root{Object}.piece{Array}{Object}
root{Object}.piece{Array}{Object}.itemId
root{Object}.piece{Array}{Object}.num
root{Object}.soulList{Array}
root{Object}.soulList{Array}{Object}
root{Object}.soulList{Array}{Object}.breakLevel
root{Object}.soulList{Array}{Object}.id
root{Object}.soulList{Array}{Object}.mosaicPosition
root{Object}.soulList{Array}{Object}.skillPosition
root{Object}.soulList{Array}{Object}.strengthLevel
root{Object}.soulList{Array}{Object}.uuid
root{Object}.weaponList{Array}
root{Object}.weaponList{Array}{Object}
root{Object}.weaponList{Array}{Object}.exp
root{Object}.weaponList{Array}{Object}.isOk
root{Object}.weaponList{Array}{Object}.isWear
root{Object}.weaponList{Array}{Object}.level
root{Object}.weaponList{Array}{Object}.skillId
root{Object}.weaponList{Array}{Object}.valueList{Array}
root{Object}.weaponList{Array}{Object}.valueList{Array}{Array}
root{Object}.weaponList{Array}{Object}.weaponId
root{Object}.weaponNew
root{Object}.wearCharmList{Array}

In the 'root{Object}.wearCharmList{Array}', 'root{Object}' means the 'root' is an object. 'wearCharmList{Array}' means the 'wearCharmList' is a list and the objs in 'wearCharmList' are simple class(Just like int, String...) attrs.
In the 'root{Object}.weaponList{Array}{Object}', 'weaponList{Array}{Object}' means 'weaponList' is a list and the objs in 'weaponList' is an object.If you want to know the objs attributes, you can see the next line, like 'root{Object}.weaponList{Array}{Object}.exp', this means the object contains an attribute 'exp'.

Then you can use Json.select(), and input the 'root.weaponList.exp' to 'executeLanguage'.

JSON.select("root.weaponList.exp", json)

The result is:
[0, 0]

That means in the 'weaponList' has 2 objects.The 'exp' in the objects.
If you want to get the first object's exp. you can input 'root.weaponList[0].exp'.

JSON.select("root.weaponList[0].exp", json)

The result is:
[0]