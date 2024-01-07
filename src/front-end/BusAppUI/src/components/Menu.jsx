//React
import React, { useEffect } from 'react'

//MUI
import { styled } from '@mui/material/styles'
import { grey } from '@mui/material/colors'
import Box from '@mui/material/Box'
import Drawer from '@mui/material/SwipeableDrawer'
import Skeleton from '@mui/material/Skeleton'
import DragHandleIcon from '@mui/icons-material/DragHandle'
import List from '@mui/material/List'
import ListItem from '@mui/material/ListItem'
import ListItemText from '@mui/material/ListItemText'
import ListItemButton from '@mui/material/ListItemButton'
import { useTheme } from '@emotion/react'
import TextField from '@mui/material/TextField'
import InputAdornment from '@mui/material/InputAdornment'
import SearchIcon from '@mui/icons-material/Search'

const AgencyLink = ({agencyTag, location, handleClick}) => {
  return (
    <>
      <ListItemButton
        sx={{
          borderRadius: 1,
          width: '100%',
        }}
      >
        <ListItemText>{agencyTag}</ListItemText>
      </ListItemButton>
    </>
  )
}

const MenuList = ({ list }) => {
  return (
    <>
      <List>
        {
          list.map((agencyTag) => {
            return (
              <AgencyLink
                agencyTag={agencyTag}
              />
            )
          })
        }
      </List>
    </>
  )
}

const SearchBox = () => {
  return (
    <TextField
          sx={{
            mb: 2, // Add some bottom margin to separate it from the DragHandleIcon
            width: '100%', // Adjust as needed
            px: 2,
          }}
          variant="outlined"
          placeholder="Search"
          InputProps={{
            startAdornment: (
              <InputAdornment position="start">
                <SearchIcon />
              </InputAdornment>
            ),
          }}
    />
  )
}

const agencyTags = ["yessir", "yeah", "Malborough"]

const Menu = () => {
  const [open, setOpen] = React.useState(false)

  const toggleDrawer = (open) => {
    setOpen(open)
  }

  //prop constants
  const ANCHOR = 'bottom'

  //style constants
  const DRAWER_BLEEDING = 103
  const ICON_WIDTH = '16px'
  const DRAWER_HEIGHT = '75%'


  const theme = useTheme()

  return (
    <>
      <Drawer
        sx={{ 
          backgroundColor: 'white',
          '.MuiPaper-root': {
            // borderRadius: `${theme.spacing(1)} ${theme.spacing(1)} 0 0`,
            height: `calc(${DRAWER_HEIGHT} - ${DRAWER_BLEEDING}px)`,
            overflow: 'visible'
          }
        }}
        swipeAreaWidth={DRAWER_BLEEDING}
        anchor={ANCHOR}
        open={open}
        onClose={() => {toggleDrawer(false)}}
        onOpen={() => {toggleDrawer(true)}}
      >
        <Box
          sx={{
            border: 'none',
            backgroundColor: 'white',
            position: 'absolute',
            top: -DRAWER_BLEEDING,
            visibility: 'visible',
            borderRadius: `${theme.spacing(1)} ${theme.spacing(1)} 0 0`,
            right: 0,
            left: 0,
            elevation: 0,
          }}
        >
          <DragHandleIcon
            sx={{
            position: 'relative', left: `calc(50% - ${ICON_WIDTH} / 2)`,
            }}
          >
          </DragHandleIcon>
          <SearchBox/>
        </Box>
        <Box
          sx={{
            px: 2,
            py: 2,
            height: '100%',
            overflow: 'auto',
          }}
        >
          <Box
            sx={{
              borderRadius: 1,
              border: `1px solid ${theme.palette.grey.A400}`,
              height: '100%',
            }}
          />
            {/* <MenuList list={agencyTags}> */}
        </Box>
      </Drawer>
    </>
  )
}

export default Menu