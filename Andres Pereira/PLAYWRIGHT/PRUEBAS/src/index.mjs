import { chromium } from "playwright";

const broswer = await chromium.launch({ handless: true });

const page = await broswer.newPage();

await page.goto("https://www.apolosystemas.co/", {
  waitUntil: "domcontentloaded",
});

await page.waitForSelector(".icon-holder");

const existe = await page.$(".icon-holder");

if (!existe) {
  console.log("img no encontrada");
} else {
  const cards = await page.$$eval(".icon-holder", (i) =>
    i.map((e) => {
      const img= document.querySelector("img")
      const url = img.getAttribute("src");
      const title = img.getAttribute("alt")
      return { url,title };
    })
  );
  console.log(cards);
  await broswer.close();
}
