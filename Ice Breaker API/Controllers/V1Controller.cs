using System;
using System.Collections.Generic;
using System.Linq;
using System.Web;
using System.Web.Mvc;
using System;
using Ice_Breaker_API.Models;
using Newtonsoft.Json;


namespace Ice_Breaker_API.Controllers
{
    public class V1Controller : Controller
    {

        // GET: V1

        public string GetUser(string email)
        {
            IceBreakDBDataContext db = new IceBreakDBDataContext();
            var user = db.GET_User(email).First();
            var json = System.Web.Helpers.Json.Encode(new { email = user.email, name = user.name, photoUrl = user.photoURL, token = user.token });
            return json;
        }

        //public bool Add(string jsonUserData)
        //{
        //    jsonUserData = "{'email':'Soumya@gmail.com','name':'Soumya','token':'THISISME','photoURL':'thisimg.png'}";  //testing purpose
        //    if (jsonUserData != null)
        //    {
        //        UserData data = new UserData();
        //        data = JsonConvert.DeserializeObject<UserData>(jsonUserData);
        //        IceBreakDBDataContext db = new IceBreakDBDataContext();
        //        db.ADD_User(data.email, data.name, data.token, data.photoURL);
        //        return true;
        //    }
        //    else
        //        return false;


        //}


        /// <summary>
        /// 
        /// </summary>
        /// <param name="email"></param>
        /// <param name="longitude"></param>
        /// <param name="latitude"></param>
        /// <returns></returns>
        public string SetActiveUser(string email, float longitude, float latitude)
        {
            try
            {
                IceBreakDBDataContext db = new IceBreakDBDataContext();
                db.SET_User_Active(email, longitude, latitude);
                var users = db.GET_Active_Users();  //gets list of all active users 

                var userData = users.Where(m => distance(m.Latitude, m.Longitude, latitude, longitude, 'K') < 0.10);
                //var useremails = users.Where(m => m.Email == "asdf@adsf.com");
                string output = JsonConvert.SerializeObject(userData);
                return output;
            }
            catch
            {
                return String.Empty;
            }

        }


        /// <summary>
        /// Add a user to the database
        /// </summary>
        /// <param name="jsonUserData">{ 'email': String, 'name': String, 'token': String, 'photoURL': String }</param>
        /// <returns>true on success; false on fail</returns>
        public string Add(string jsonUserData)
        {
            if (jsonUserData != null)
            
            {
                UserData data = new UserData();
                data = JsonConvert.DeserializeObject<UserData>(jsonUserData);
                IceBreakDBDataContext db = new IceBreakDBDataContext();
                db.ADD_User(data.email, data.name, data.token, data.photoURL);
                return System.Web.Helpers.Json.Encode(new { output = true });
            }
            else
                return System.Web.Helpers.Json.Encode(new { output = true });           

        }
        /// <summary>
        /// Set a user inactive when they turn off their app
        /// </summary>
        /// <param name="email">User's Email</param>
        /// <returns>true if action completed; false if action failed</returns>
        public bool SetUserInactive(string email)
        {
            if (email != null)
            {

                IceBreakDBDataContext db = new IceBreakDBDataContext();
                db.SET_User_Inactive(email);
                return true;

            }
            else
                return false;
        }
        /// <summary>
        /// Add a user's interest
        /// </summary>
        /// <param name="email">User Email Address</param>
        /// <param name="interest">interest foreign key</param>
        /// <returns>True on success; false on fail</returns>
        public bool AddUserInterest(string email, string interest)
        {
            if (email != null && interest != null)
            {
                IceBreakDBDataContext db = new IceBreakDBDataContext();
                db.ADD_User_Interest(email, interest);
                return true;
            }
            else
            {
                return false;
            }
        }



        /// <summary>
        /// find the distance between two points using longitude and latitude
        /// </summary>
        /// <param name="lat1">Latitude of point 1</param>
        /// <param name="lon1">Longitude of point 1</param>
        /// <param name="lat2">Latitude of Point 2</param>
        /// <param name="lon2">Longitude of Point 2</param>
        /// <param name="unit">Unit of Measure:K for Kilometers, N for nautical miles, m for miles</param>
        /// <returns></returns>
        private double distance(double lat1, double lon1, double lat2, double lon2, char unit)
        {
            double theta = lon1 - lon2;
            double dist = Math.Sin(deg2rad(lat1)) * Math.Sin(deg2rad(lat2)) + Math.Cos(deg2rad(lat1)) * Math.Cos(deg2rad(lat2)) * Math.Cos(deg2rad(theta));
            dist = Math.Acos(dist);
            dist = rad2deg(dist);
            dist = dist * 60 * 1.1515;
            if (unit == 'K')
            {
                dist = dist * 1.609344;
            }
            else if (unit == 'N')
            {
                dist = dist * 0.8684;
            }
            return (dist);
        }

        //:::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::
        //::  This function converts decimal degrees to radians             :::
        //:::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::
        private double deg2rad(double deg)
        {
            return (deg * Math.PI / 180.0);
        }

        //:::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::
        //::  This function converts radians to decimal degrees             :::
        //:::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::
        private double rad2deg(double rad)
        {
            return (rad / Math.PI * 180.0);
        }


        public string AddBrokenIce(string emailId, string connectedId, double latitude, double longitude)
        {
            IceBreakDBDataContext db = new IceBreakDBDataContext();
            try
            {
                db.ADD_BrokenIce(emailId, connectedId, latitude, longitude);
                return System.Web.Helpers.Json.Encode(new { output = true });

            }

            catch (Exception ex)
            {
                return System.Web.Helpers.Json.Encode(new { output = false });
            }

        }

    }
}