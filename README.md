# Android-Practices

Fourth practice

The application allows you to get the user's VKontakte ID to get his albums and view photos in albums. The app is available in two languages (English and Russian), and has a portrait and landscape adaptive layout. All albums and images are loaded asynchronously and in packages (when the user has scrolled to the end of the list, the next package is loaded). The app can retrieve images from the cache as well (including if the Internet connection is lost). Unloaded images, as well as images that failed to load, are replaced by placeholders.

The following functionality is implemented:
1. Main window with user ID input (only integer number)
<details>
  <summary>Example</summary>
  <img src="https://i.ibb.co/XCktsT4/1.jpg">
</details>

2. Display albums
<details>
  <summary>Example</summary>
  <img src="https://i.ibb.co/cTxSSxF/2.jpg">
</details>

3. Handling various errors (input and VK API) 
<details>
  <summary>Example</summary>
  <img src="https://i.ibb.co/qxZNF2g/3.jpg">
</details>

4. Display photos of specific album
<details>
  <summary>Example</summary>
  <img src="https://i.ibb.co/9wCLPz1/4.jpg">
</details>
