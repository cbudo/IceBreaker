using System;
using System.Collections.Generic;
using System.Linq;
using System.Web;
using System.Web.Mvc;

namespace Ice_Breaker_API.Controllers
{
    public class HomeController : Controller
    {

        public ActionResult Index()
        {
            ViewBag.Title = "Home Page";
            return View();
        }
        public ActionResult Dash()
        {
            IceBreakDBDataContext db = new IceBreakDBDataContext();
            try
            {
                var breaks = db.GET_BreaksInIce().Where(m=>m.Breaks>0).OrderByDescending(m=>m.Breaks);
                return View(breaks);
            }
            catch
            {
                return RedirectToAction("Index");
            }
        }
    }
}
