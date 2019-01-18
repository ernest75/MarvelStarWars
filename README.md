# MarvelStarWars

## Considerations about the app

To make the assigment I adopted MVP architecture and I used the following libraries: RxJava2, Retrofit, Dagger2, Glide amd Butterknife

I could'nt manage to get any working key from the Marvel Api, I suspect that the api is still working but they don't provide new keys, the site works quite bad and the latests updates are from 2014. I also send two emails, one to the developer site itself and another to Marvel general site and didn't get any answer yet. Anw I leave all the classes and the dependency injection wired with dagger just in case I get some reply or I can get some working keys. Since I couldn't use the MarvelApi, I decided to use another library so the project will make calls to two diferent Api's, and as the app is for Mascoteros I found that an Api that shows pictures of the pets and their owners will be perfect. This Api is https://dog.ceo/api/ it's a public and free Api, witch basically shows pictures of diferent breed dogs.

Since this new Api only returns the url images of the dogs I did a bit of hacking creating the dogs name's, witch basically are dog + int(number of dog), but since the App had to show the results alphabetically I also made a method to create random chars to put as the firs character of the string dog so on the recyclerview the starwars results and the dogs results will be a bit mixed.

The Starwars Api dosen't provide pictures so I download one from the net and used as an Avatar.

For the 2 version I want to do the unit testing, improve a bit the design, and if I have time create a cache with Room.

Any comments or feedback will be very much welcome,

Thnks,

Ernest,
