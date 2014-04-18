
#This program uses HP IDOL API to check for entities. It can work with sentences from stdin, files, or URL's  (here only individual sentences are implemented)
#It could be used as a comparing benchmark

import urllib
import json


input_text = raw_input("Enter text:")
input_text=input_text.replace(" ","+")


Entities=["companies","people","places","date"];


for ent in Entities:

        base = "https://api.idolondemand.com/1/api/sync/extractentity/v1?text="+input_text+".&entity_type="+ent+"_eng&apikey=02d9253e-6a73-4192-848f-bbe35b2f0a33"
        response=urllib.urlopen(base)
        line=response.readline()

        obj = json.loads(line)
        tmplist=[]
        for i in range(0, len(obj["entities"])):
                obj2=dict(obj["entities"][i])
                tmplist.append(obj2["original_text"])
        print ent+"->"+str(tmplist)


