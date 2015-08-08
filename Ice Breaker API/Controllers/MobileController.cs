using System;
using System.Collections.Generic;
using System.Linq;
using System.Web;
using System.Web.Mvc;
using Ice_Breaker_API.Models;

namespace Ice_Breaker_API.Controllers
{
    public class MobileController : Controller
    {
        // GET: Mobile
        public ActionResult Index()
        {
            return View();
        }

        public ActionResult Nearby()
        {
            V1Controller v1 = new V1Controller();
            return View();
        }

        public ActionResult SignOut(string email)
        {
            V1Controller v1 = new V1Controller();
            v1.SetUserInactive(email);
            return RedirectToAction("signIn");
        }

        public ActionResult SignIn()
        {
            UserData model = new UserData();
            return View(model);
        }

        [HttpPost]
        public ActionResult SignIn(string email)
        {
            if (email == "cb9595@gmail.com")
            {
                return RedirectToAction("Index");
            }
            return RedirectToAction("Nearby");
        }
    }
}